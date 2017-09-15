package kbaserelationengine;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
import org.neo4j.driver.v1.summary.SummaryCounters;

public class Neo4jDataProvider {

	private String NEO4J_HOST = System.getenv("NEO4J_HOST");
	private String NEO4J_PORT = System.getenv("NEO4J_PORT");
	private String NEO4J_URL = "bolt://" + NEO4J_HOST + ":" + NEO4J_PORT;
	
	private String NEO4J_USER = System.getenv("NEO4J_USER");
	private String NEO4J_PWD = System.getenv("NEO4J_PWD");
//	private static final String TSV_FILE_NAMES_CONFIG = "tsv_files.config";
	private static final String TSV_FILE_NAMES_CONFIG = "tsv_remote_files.config";
			
	private final String LAOD_REFERNCE_DATA_CYPHER_FILE_NAME = "lib/cypher/load_reference_data.txt";
	
	private Driver driver = null;

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
		if(driver == null){
			driver = GraphDatabase.driver( NEO4J_URL, AuthTokens.basic( NEO4J_USER, NEO4J_PWD ) );
		}
		
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

	public GraphUpdateStat storeKEAppDescriptor(StoreKEAppDescriptorParams params){
		GraphUpdateStat stat = new GraphUpdateStat();
		
		Session session = getSession();
		KEAppDescriptor app = params.getKeapp();
		try{
			StatementResult res = session.run(
						"merge(a:KEApp{ "
						+ "guid:{guid},name:{name},"
						+ "version:{version},"
						+ "last_run_epoch:{last_run_epoch},"
						+ "nodes_created: {nodes_created},"
						+ "relations_created: {relations_created},"
						+ "properties_set:{properties_set}});",
					parameters(
							"guid",app.getGuid(),
							"name",app.getName(),
							"version",app.getVersion(),
							"last_run_epoch",app.getLastRunEpoch(),
							"nodes_created",app.getNodesCreated(),
							"relations_created",app.getRelationsCreated(),
							"properties_set",app.getPropertiesSet()));
			
			setCounters(stat, res.consume().counters());			
		} finally {
			session.close();
		}	
		return stat;
	}
		
	public void cleanKEAppResults(CleanKEAppResultsParams params) {
		
		Session session = getSession();
		try{
			int nodesCount = 1;
			while(nodesCount > 0){
				StatementResult res = session.run("match(r:AppResult{_appGuid:{appGuid}}) detach delete r", 
						parameters("appGuid", params.getAppGuid()));
				nodesCount = res.consume().counters().nodesDeleted();				
			}
			session.run("match(a:KEApp{guid:{appGuid}}) "
					+ " set a.last_run_epoch = 0,"
					+ " a.nodes_created = 0, "
					+ " a.relations_created = 0,"
					+ " a.properties_set = 0",
					parameters("appGuid", params.getAppGuid()));
					
		} finally {
			session.close();
		}
	}		
	
	public KEAppDescriptor getKEAppDescriptor(GetKEAppDescriptorParams params) {
		KEAppDescriptor app = new KEAppDescriptor();
		Session session = getSession();
		try{			
			StatementResult result = session.run( "match(a:KEApp{guid:{appGuid}}) "
					+ " return a.guid, a.name, a.version,a.last_run_epoch,a.nodes_created,a.relations_created,a.properties_set",
					parameters("appGuid", params.getAppGuid()));
			
			while ( result.hasNext() )
			{
			    Record record = result.next();
			    app
			    	.withGuid(record.get( "a.guid" ).asString())
			    	.withLastRunEpoch(record.get( "a.last_run_epoch" ).asLong())
			    	.withName(record.get( "a.name" ).asString())
			    	.withNodesCreated(record.get( "a.nodes_created" ).asLong())
			    	.withPropertiesSet(record.get( "a.properties_set" ).asLong())
			    	.withRelationsCreated(record.get( "a.relations_created" ).asLong())
			    	.withVersion(record.get( "a.version" ).asString());
			}				
		}finally {
			session.close();
		}
		return app;
	}
	
	private void setCounters(GraphUpdateStat stat, SummaryCounters counters){
		stat
			.withNodesCreated((long)counters.nodesCreated())
			.withRelationshipsCreated((long)counters.relationshipsCreated())
			.withPropertiesSet((long) counters.propertiesSet());
	}
	
	public GraphUpdateStat storeBiclusters(StoreBiclustersParams params){
		GraphUpdateStat stat = new GraphUpdateStat();
		
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		try{
			for(Bicluster bc: params.getBiclusters()){
				
				StatementResult res = tr.run(
					"create(b:Bicluster:AppResult{guid:{bGuid}, _appGuid:{appGuid}, featureGuids:{featureGuids}})" 
						+" with b" 
						+" match(a:KEApp{guid:{appGuid}})"
						+" create (b)-[:MY_APP]->(a)"
						+" with b"
						+" MATCH (c:Compendium{guid:{cmpGuid}})"
						+" CREATE (b)-[:MY_COMPENDIUM]->(c)"
						+" with b"
						+" MATCH  (f:Feature)"
						+" WHERE  f.guid in {featureGuids}"
						+" CREATE (f)-[:MY_BICLUSTER]->(b);",				
					parameters(
						"bGuid",bc.getGuid()
						,"appGuid",bc.getKeappGuid()
						,"cmpGuid", bc.getCompendiumGuid()
						,"featureGuids",bc.getFeatureGuids()));			
				setCounters(stat, res.consume().counters());			
			}
			tr.success();
		} finally {
			tr.close();
			session.close();
		}
		return stat;		
	}
	
	public List<CompendiumDescriptor> getCompendiumDescriptors(GetCompendiumDescriptorsParams params) {
		Session session = getSession();
		
		List<CompendiumDescriptor> cds = new ArrayList<CompendiumDescriptor>();
		try{
			String matchStatement = "";
			Value matchParameters = null;
			if(params.getTaxonomyGuid() != null){
				matchStatement = "match(t:Taxon{guid:{tguid}})-[:MY_TAXON]-(c:Compendium) ";
				matchParameters = parameters( "tguid", params.getTaxonomyGuid() );
			} else if (params.getDataType()!= null){
				matchStatement = "match(t:Taxon)-[:MY_TAXON]-(c:Compendium{type:{ctype}}) ";
				matchParameters = parameters( "ctype", params.getDataType() );
			}
			
			if(matchStatement.length() > 0){
				String statement = matchStatement + " return t.guid,c.guid,c.type,c.name,c.ws_id";
				StatementResult result = session.run( statement, matchParameters);				
				while ( result.hasNext() )
				{
				    Record record = result.next();
				    cds.add(new CompendiumDescriptor()
				    		.withDataType(record.get( "c.type" ).asString())
				    		.withGuid(record.get( "c.guid" ).asString())
				    		.withName(record.get( "c.name" ).asString())
				    		.withTaxonomyGuid(record.get( "t.guid" ).asString())
				    		.withWsNdarrayId(record.get( "c.ws_id" ).asString()));
				}				
			}
			
		}finally {
			session.close();
		}
		return cds;
	
	}

	public List<Bicluster> getBiclusters(GetBiclustersParams params) {
		Session session = getSession();
		
		Hashtable<String,Bicluster> guid2bicluster = new Hashtable<String,Bicluster>(); 
		
		List<CompendiumDescriptor> cds = new ArrayList<CompendiumDescriptor>();
		try{			
			StringBuffer sb = new StringBuffer();
			for(String guid: params.getBiclusterGuids()){
				if(sb.length() > 0){
					sb.append(",");
				}
				sb.append("'" + guid + "'");
			}
			String guidArray = sb.toString();			
			
			// Get biclusters metadata 
			StatementResult result = session.run( "match(b:Bicluster) where b.guid in [" + guidArray + "] return b.guid, b._appGuid, b.featureGuids;");
			while ( result.hasNext() )
			{
			    Record record = result.next();
			    Bicluster b = new Bicluster()
			    		.withCompendiumGuid(null)
			    		.withConditionGuids(null)
			    		.withFeatureGuids(new ArrayList(record.get("b.featureGuids").asList()))
			    		.withGuid(record.get( "b.guid" ).asString())
			    		.withKeappGuid(record.get( "b._appGuid" ).asString());
			    					    	
			    System.out.println(record.get("b.featureGuids").getClass().getName()); 			    
			    guid2bicluster.put(b.getGuid(), b);
			}				
		}finally {
			session.close();
		}
		return new ArrayList<Bicluster>(guid2bicluster.values());
	}
	
	
	public List<BiclusterDescriptor> getBiclusterDescriptors(GetBiclusterDescriptorsParams params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) throws IOException {
//		new Neo4jDataProvider(null).loadReferenceData();
//		String[][] _bis = new String[][]{
//			{"BIC:1234","KEApp1", "KBaseGen123;KBaseGen120;KBaseGen121"},
//			{"BIC:3422","KEApp1", "KBaseGen101;KBaseGen100;KBaseGen104;KBaseGen111"},
//			{"BIC:3451","KEApp1", "KBaseGen101;KBaseGen101;KBaseGen104;KBaseGen121"}
//		};
//		List<Bicluster> biclusters = new ArrayList<Bicluster>();
//		for(String[] vals: _bis){
//			biclusters.add(new Bicluster()
//					.withGuid(vals[0])
//					.withKeappGuid(vals[1])
//					.withFeatureGuids(Arrays.asList(vals[2].split(";"))));			
//		}
//		new Neo4jDataProvider(null).storeBiclusters(new StoreBiclustersParams().withBiclusters(biclusters) );		
		
//		List<Bicluster> items = new Neo4jDataProvider(null).getBiclusters(new GetBiclustersParams().withBiclusterGuids(
//				Arrays.asList(new String[]{"BIC:1234", "BIC:3422"})
//				));
//		for(Bicluster b: items){
//			System.out.println(b);
//		}
		
//		List<CompendiumDescriptor> items = new Neo4jDataProvider(null).getCompendiumDescriptors(new GetCompendiumDescriptorsParams()
//				.withDataType("gene expression"));
//		for(CompendiumDescriptor item: items){
//			System.out.println(item);
//		}

		new Neo4jDataProvider(null).cleanKEAppResults(new CleanKEAppResultsParams().withAppGuid("KEApp1"));
		
		KEAppDescriptor res = new Neo4jDataProvider(null).getKEAppDescriptor(new GetKEAppDescriptorParams().withAppGuid("KEApp1"));
		System.out.println(res);
		
	}
}
