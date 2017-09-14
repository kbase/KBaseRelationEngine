package kbaserelationengine;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.summary.ResultSummary;

public class Neo4jDataProvider {

	String NEO4J_HOST = System.getenv("NEO4J_HOST");
	String NEO4J_PORT = System.getenv("NEO4J_PORT");
	String NEO4J_URL = "bolt://" + NEO4J_HOST + ":" + NEO4J_PORT;
	
	String NEO4J_USER = System.getenv("NEO4J_USER");
	String NEO4J_PWD = System.getenv("NEO4J_PWD");
	private static final String TSV_FILE_NAMES_CONFIG = "tsv_files.config";
		
	private final String LAOD_REFERNCE_DATA_CYPHER_FILE_NAME = "lib/cypher/load_reference_data.txt";
	
	private final Driver driver = GraphDatabase.driver( NEO4J_URL, AuthTokens.basic( NEO4J_USER, NEO4J_PWD ) );

	class CypherStatement{
		String name;
		String statement;
		Long runTime;	
		int nodesCreated;
		int relationsCreated;
		int propertiesSet;
		public CypherStatement(String name, String statement) {
			super();
			this.name = name;
			this.statement = statement;
		}		
	}
	
	public Neo4jDataProvider(Map<String,String> config) {
		NEO4J_HOST = getConfigValue(config,"NEO4J_HOST");
		NEO4J_PORT = getConfigValue(config,"NEO4J_PORT");
		NEO4J_URL = "bolt://" + NEO4J_HOST + ":" + NEO4J_PORT;
		
		NEO4J_USER = getConfigValue(config,"NEO4J_USER");
		NEO4J_PWD = getConfigValue(config,"NEO4J_PWD");		
	}
	
	private String getConfigValue(Map<String,String> config, String key){
		String value = config == null ? null : config.get(key);
		return value == null ? System.getenv(key): value;
	}
	
	private Session getSession(){
		return driver.session();
	}
	
	public void loadReferenceData() throws IOException{
		List<CypherStatement> cypherStatements = loadCypherStatements();
		Session session = getSession();
		try{
			for(CypherStatement st: cypherStatements){
				System.out.println("========");
				System.out.println(st.name);
				System.out.println("========");
				System.out.println(st.statement);
				
				
				System.out.print("Started...");
				long timeStart = System.currentTimeMillis();
				StatementResult res = session.run(st.statement);
				ResultSummary summary = res.consume();
				st.nodesCreated = summary.counters().nodesCreated();
				st.relationsCreated = summary.counters().relationshipsCreated();
				st.propertiesSet = summary.counters().propertiesSet();
				st.runTime = System.currentTimeMillis() - timeStart;
				System.out.println("Done! " 
						+ "\trunTime = "+ (st.runTime)
						+ "\tnodesCreated = "+ (st.nodesCreated)
						+ "\trelationsCreated = "+ (st.relationsCreated)
						+ "\tpropertiesSet = "+ (st.propertiesSet)
						);
				
				System.out.println("");
			}			
		} finally{
			session.close();
		}
	}
	
	
	private List<CypherStatement> loadCypherStatements() throws IOException {
		List<CypherStatement> statements = new ArrayList<CypherStatement>();
		BufferedReader br = new BufferedReader(new FileReader(LAOD_REFERNCE_DATA_CYPHER_FILE_NAME));
		try{
			String name = "";
			StringBuffer statement = new StringBuffer();
			for(String line = br.readLine(); line != null; line = br.readLine()){
				line = line.trim();
				if(line.startsWith(">")){
					if(!name.startsWith("#") && statement.length() > 0){
						statements.add(new CypherStatement(name, statement.toString()));
					} 
					name = line.substring(1);
					statement.setLength(0);
				}else{
					statement.append(" ");
					statement.append(line);
				}				
			}
			// Final check
			if(! name.startsWith("#") && statement.length() > 0){
				statements.add(new CypherStatement(name, statement.toString()));
			}
		}finally {
			br.close();
		}
		
		setFileNames(statements);
		
		return statements;
	}

	private void setFileNames(List<CypherStatement> statements) throws IOException {
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream(TSV_FILE_NAMES_CONFIG));
		for( Entry<Object, Object> entry: props.entrySet()){
			String fileTag = (String)entry.getKey();
			String fileName = (String)entry.getValue();
			System.out.println("Updateing: " + fileTag + "\t" + fileName);
			for(CypherStatement st: statements){
				st.statement = st.statement.replaceAll("\\{" + fileTag + "\\}", fileName);
			}
		}
	}
	
	public List<FeatureSequence> getFeatureSequences(GetFeatureSequencesParams params){
		List<FeatureSequence> fss = new ArrayList<FeatureSequence>();
		Session session = getSession();
		try{
			String matchStatement = "";
			Value matchParameters = null;
			if(params.getTaxonomyGuid() != null){
				matchStatement = "match(t:Taxon{guid:{tguid}})-[:MY_TAXON]-(f:Feature) ";
				matchParameters = parameters( "tguid", params.getTaxonomyGuid() );
			} else if (params.getOrthologGuid() != null){
				matchStatement = "match(og:OrthologGroup{guid:{ogguid}})-[:MY_ORTHOLOG_GROUP]-(f:Feature)-[:MY_TAXON]-(t:Taxon) ";				
				matchParameters = parameters( "ogguid", params.getOrthologGuid() );
			} else if (params.getGotermGuid() != null){
				matchStatement = "match(gt:GOTerm{guid:{goguid}})-[:MY_OTERM]-(f:Feature)-[:MY_TAXON]-(t:Taxon) ";				
				matchParameters = parameters( "goguid", params.getGotermGuid() );
			} 
			
			if(matchStatement.length() > 0){
				String statement = matchStatement + " return t.guid, f.guid, f.sequence";
				StatementResult result = session.run( statement, matchParameters);				
				while ( result.hasNext() )
				{
				    Record record = result.next();
				    fss.add(new FeatureSequence()
				    		.withTaxonomyGuid(record.get( "t.guid" ).asString())
				    		.withFeatureGuid(record.get( "f.guid" ).asString())
				    		.withProteinSequence(record.get( "f.sequence" ).asString()));
				}				
			}
			
		}finally {
			session.close();
		}
		return fss;
	} 

	public void storeKEAppDescriptor(StoreKEAppDescriptorParams params){
		Session session = getSession();
		KEAppDescriptor app = params.getKeapp();
		try{
			session.run("merge(a:KEApp{ guid:{guid},name:{name},version:{version},last_run_epoch:{last_run_epoch},nodes_created: {nodes_created},relations_created: {relations_created},properties_set:{properties_set}});",
					parameters(
							"guid",app.getGuid(),
							"name",app.getName(),
							"version",app.getVersion(),
							"last_run_epoch",app.getLastRunEpoch(),
							"nodes_created",app.getNodesCreated(),
							"relations_created",app.getRelationsCreated(),
							"properties_set",app.getPropertiesSet())
			);
		} finally {
			session.close();
		}		
	}
	
	public void storeBiclusters(StoreBiclustersParams params){
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		try{
			for(Bicluster bc: params.getBiclusters()){
				
				tr.run(
					"create(b:Bicluster:AppResult{guid:{bGuid}, _appGuid:{appGuid}})" 
						+" with b" 
						+" match(a:KEApp{guid:{appGuid}})"
						+" create (b)-[:MY_APP]->(a)"
						+" with b"
						+" MATCH  (f:Feature)"
						+" WHERE  f.guid in {featureGuids}"
						+" CREATE (f)-[:MY_BICLUSTER]->(b);",				
					parameters(
						"bGuid",bc.getGuid()
						,"appGuid",bc.getKeappGuid()
						,"featureGuids",bc.getFeatureGuids()));				
			}
			tr.success();
		} finally {
			tr.close();
			session.close();
		}
	}
	
}
