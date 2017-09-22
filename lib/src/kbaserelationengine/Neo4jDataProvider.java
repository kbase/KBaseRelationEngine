package kbaserelationengine;

import static org.neo4j.driver.v1.Values.parameters;
import static org.neo4j.driver.v1.Values.value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
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
				if(line.startsWith("#")) continue;
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
		KEAppDescriptor app = params.getApp();
		try{
			StatementResult res = session.run(
						"merge(a:KEApp{guid:{guid}}) "
						+ " set a.name={name},"
						+ " a.version={version},"
						+ " a.last_run_epoch={last_run_epoch},"
						+ " a.nodes_created={nodes_created},"
						+ " a.relations_created={relations_created},"
						+ " a.properties_set={properties_set};",
					parameters(
							"guid",app.getGuid(),
							"name",app.getName(),
							"version",app.getVersion(),
							"last_run_epoch",app.getLastRunEpoch(),
							"nodes_created",app.getNodesCreated(),
							"relations_created",app.getRelationsCreated(),
							"properties_set",app.getPropertiesSet()));
			
			updateCounters(stat, res.consume().counters());			
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
	
	private void updateCounters(GraphUpdateStat stat, SummaryCounters counters){
		Long nodesCreated = stat.getNodesCreated() != null ? stat.getNodesCreated(): 0;
		Long nodesDeleted = stat.getNodesDeleted() != null ? stat.getNodesDeleted(): 0;
		Long relationshipsCreated = stat.getRelationshipsCreated() != null ? stat.getRelationshipsCreated(): 0;
		Long relationshipsDeleted = stat.getRelationshipsDeleted() != null ? stat.getRelationshipsDeleted(): 0;
		Long propertiesSet = stat.getPropertiesSet() != null ? stat.getPropertiesSet(): 0;
		
		stat
			.withNodesCreated(nodesCreated +  counters.nodesCreated())
			.withNodesDeleted(nodesDeleted + counters.nodesDeleted())
			.withRelationshipsCreated(relationshipsCreated + counters.relationshipsCreated())
			.withRelationshipsDeleted(relationshipsDeleted + counters.relationshipsDeleted())
			.withPropertiesSet(propertiesSet + counters.propertiesSet());
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
				updateCounters(stat, res.consume().counters());			
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
				matchStatement = "match (c:Compendium{type:{ctype}}) optional match (c)-[:MY_TAXON]-(t:Taxon) ";
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

	class CypherWhereBuilder{
		class Param{
			String fieldName;
			String paramName;
			Object value;
		}
		List<Param> params = new ArrayList<Param>();
		
		public void init(){
			params.clear();
		}
		
		public int size(){
			return params.size();
		}
		
		public void addCondition(String fieldName, String paramName, Object value){
			Param p = new Param();
			p.fieldName = fieldName;
			p.paramName = paramName;
			p.value = value;
			params.add(p);
		}
		
		public String toWehreStatement(){
			StringBuffer sb = new StringBuffer();
			for(Param p: params){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append(" " + p.fieldName + "=" + "{" + p.paramName + "} ");
			}
			return sb.toString();
		}
		
		public Value toParameters()
		{
			Map<String,Object> ps = new Hashtable<String, Object>();
			for(Param p: params){
				ps.put(p.paramName, p.value);
			}
			return value(ps);
		}
	}
	
	
	public List<Bicluster> getBiclusters(GetBiclustersParams params) {
		Session session = getSession();
		List<Bicluster> biclusters = new ArrayList<Bicluster>();
		try{		
			
			CypherWhereBuilder wb = new CypherWhereBuilder();
			if(params.getKeappGuid() != null){
				wb.addCondition("b._appGuid", "appGuid", params.getKeappGuid());
			}
			if (params.getTaxonomyGuid() != null){
				wb.addCondition("t.guid", "tGuid", params.getTaxonomyGuid());
			}
			if (params.getCompendiumGuid() != null){
				wb.addCondition("c.guid", "cGuid", params.getCompendiumGuid());
			}
						
			if(wb.size() > 0){
				String statement =
						"match(b:Bicluster)--(c:Compendium)--(t:Taxon) "
						+ " where " + wb.toWehreStatement()
						+ " return b.guid, b._appGuid, b.featureGuids,c.guid,t.guid";
				StatementResult result = session.run( statement, wb.toParameters());				
				while ( result.hasNext() )
				{
				    Record record = result.next();
				    biclusters.add(new Bicluster()
				    		.withCompendiumGuid(record.get( "c.guid" ).asString())
				    		.withConditionGuids(null)
				    		.withFeatureGuids(new ArrayList(record.get("b.featureGuids").asList()))
				    		.withGuid(record.get( "b.guid" ).asString())
				    		.withKeappGuid(record.get( "b._appGuid" ).asString())
				    		.withTaxonomyGuid(record.get( "t.guid" ).asString()));				    		
				}				
			}			
				
		}finally {
			session.close();
		}
		return biclusters;
	}	
	
	public GraphUpdateStat storeWSGenome(StoreWSGenomeParams params) {
		GraphUpdateStat stat = new GraphUpdateStat();
//		String fguids = String.join(";", params.getFeatureGuids());			
		StringBuffer sb = new StringBuffer();
		for(String guid: params.getFeatureGuids()){
			if(sb.length() > 0) sb.append(";");
			sb.append(guid);
		}
		String fguids = sb.toString();
		
		Session session = getSession();
		try{			
			StatementResult res = session.run("create(g:WSGenome{guid:{gGuid}, wsId:{wsGuid}}) "
					+ " with g "
					+ " foreach (fguid in split({fguids},';')  | "
					+ " create(f:WSFeature{guid:fguid}) "
					+ " create (f)-[:MY_GENOME]->(g) )",
					parameters("gGuid", params.getGenomeRef()
							,"wsGuid", params.getGenomeRef()
							,"fguids", fguids));			
			updateCounters(stat, res.consume().counters());			
		} finally {
			session.close();
		}
		return stat;	
	}

	public GraphUpdateStat storeRichWSGenome(StoreRichWSGenomeParams params) {
		GraphUpdateStat stat = new GraphUpdateStat();		
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		try{
			// First create genome node
			StatementResult res;
			
			res = tr.run("create(g:WSGenome{guid:{gGuid}, wsId:{wsGuid}}) ",
					parameters("gGuid", params.getGenomeRef()
							,"wsGuid", params.getGenomeRef()));					
			updateCounters(stat, res.consume().counters());			
			
			// create features
			for(WSFeature feature: params.getFeatures()){
				
				String refFeatureGuid  = params.getWs2refFeatureGuids().get(feature.getGuid());
				if(refFeatureGuid==null){
					refFeatureGuid = "";
				}
				
				res = tr.run(
					"match(wg:WSGenome{guid:{gGuid}}) "
					+ " create(wf:WSFeature{guid:{fguid}, name:{fname}, ref_term_guid:{termGuid} }) "
					+ " with wg,wf "
					+ " create (wf)-[:MY_GENOME]->(wg) "
					+ " with wf "
					+ " match(rf:Feature{guid:{refFeatureGuid}})-[r:MY_ORTHOLOG_GROUP]-(og:OrthologGroup)"
					+ " CREATE (wf)-[:MY_ORTHOLOG_GROUP]->(og) ",				
					parameters(
						"gGuid", params.getGenomeRef()
						,"fguid", feature.getGuid()
						,"fname", feature.getName()
						,"termGuid", feature.getRefTermGuid()
						,"refFeatureGuid", refFeatureGuid));			
				updateCounters(stat, res.consume().counters());			
			}
			tr.success();
		} finally {
			tr.close();
			session.close();
		}
		return stat;	
	}
	
	
	public GraphUpdateStat connectWSFeatures2RefOrthologs(ConnectWSFeatures2RefOrthologsParams params) {
		GraphUpdateStat stat = new GraphUpdateStat();		
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		try{
			for(Entry<String, String> entry: params.getWs2refFeatureGuids().entrySet()){
				String wsFeatureGuid = entry.getKey();
				String refFeatureGuid = entry.getValue();
				
				StatementResult res = tr.run(
					"match(wf:WSFeature{guid:{wsFeatureGuid}}), "
					+ " (rf:Feature{guid:{refFeatureGuid}})-[r:MY_ORTHOLOG_GROUP]-(og:OrthologGroup)"
					+ " CREATE (wf)-[:MY_ORTHOLOG_GROUP]->(og)",				
					parameters(
						"wsFeatureGuid", wsFeatureGuid
						,"refFeatureGuid", refFeatureGuid));			
				updateCounters(stat, res.consume().counters());			
			}
			tr.success();
		} finally {
			tr.close();
			session.close();
		}
		return stat;	
	}

	public GraphUpdateStat connectWSFeatures2RefOTerms(ConnectWSFeatures2RefOTermsParams params) {
		GraphUpdateStat stat = new GraphUpdateStat();		
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		try{
			for(Entry<String, List<String>> entry: params.getFeature2termList().entrySet()){
				String wsFeatureGuid = entry.getKey();
				List<String> termGuids = entry.getValue();
				
				// For now, we will just set terms				
				StatementResult res = tr.run(
					"match(f:WSFeature{guid:{wsFeatureGuid}}) "
					+ " set f.termGuids = {termGuids}",
					parameters(
						"wsFeatureGuid", wsFeatureGuid
						,"termGuids", termGuids));			
				updateCounters(stat, res.consume().counters());			
			}
			tr.success();
		} finally {
			tr.close();
			session.close();
		}
		return stat;
	}	
	
	public GraphUpdateStat detachDelete(String type, int batchCount, boolean deleteAll) {
		GraphUpdateStat stat = new GraphUpdateStat();		
		Session session = getSession();
		try{
			int nodesDeleted = 1;
			int cycleIdex = 1;
			while(nodesDeleted > 0){				
				StatementResult res = session.run("match(t:"+type+") with t limit " + batchCount
						+ " detach delete t");	
				SummaryCounters counters = res.consume().counters();
				updateCounters(stat, counters);
				nodesDeleted = counters.nodesDeleted();
				if(!deleteAll) break;
				System.out.print(".");
				if(cycleIdex %10 == 0){
					System.out.println("" + cycleIdex + ": deleted so far=" + stat.getNodesDeleted() + " ");					
				}
				cycleIdex++;
			}
		}finally{
			session.close();
		}
		return stat;			
	}
	
	public GraphUpdateStat storeTermEnrichmentProfiles(StoreTermEnrichmentProfilesParams params) {
		GraphUpdateStat stat = new GraphUpdateStat();
		
		Session session = getSession();
		Transaction tr = session.beginTransaction();				
		try{			
			for(TermEnrichmentProfile tp: params.getProfiles()){
				
				List<String> termGuids = new ArrayList<String>(); 
				List<Double> pvalues = new ArrayList<Double>(); 
				List<Long> sampleCounts = new ArrayList<Long>(); 
				List<Long> expectedCounts = new ArrayList<Long>(); 
				List<Long> totalCounts = new ArrayList<Long>(); 
				for(TermEnrichment te: tp.getTerms()){
					termGuids.add(te.getTermGuid());
					pvalues.add(te.getPValue());
					sampleCounts.add(te.getSampleCount());
					expectedCounts.add(te.getExpectedCount());
					totalCounts.add(te.getTotalCount());
				}
				
				StatementResult res = tr.run(
					"create(tp:TermEnrichmentProfile:AppResult{"
						+ "guid:{tpGuid}"
						+ ", _appGuid:{appGuid}"
						+ ", termSpace:{termSpace}"
						+ ", termGuids:{termGuids}"
						+ ", pvalues:{pvalues}"
						+ ", sampleCounts:{sampleCounts}"
						+ ", expectedCounts:{expectedCounts}"
						+ ", totalCounts:{totalCounts}"
						+ "})" 
						+" with tp" 
						+" match(a:KEApp{guid:{appGuid}})"
						+" create (tp)-[:MY_APP]->(a)"
						
						+" with tp"
						+" MATCH (s:" + tp.getSourceGeneSetType() + "{guid:{sGuid}})"
						+" CREATE (tp)-[:MY_FEATURE_SET]->(s)",
					parameters(
						"tpGuid",tp.getGuid()
						,"appGuid",tp.getKeappGuid()
						,"termSpace", tp.getTermNamespace()
						,"termGuids", termGuids
						,"pvalues",pvalues
						,"sampleCounts",sampleCounts
						,"expectedCounts",expectedCounts
						,"totalCounts",totalCounts
						,"sGuid", tp.getSourceGeneSetGuid()
					));			
				updateCounters(stat, res.consume().counters());			
			}
			tr.success();
		} finally {
			tr.close();
			session.close();
		}
		return stat;	
	}

	public List<FeatureTerms> getFeatureTerms(GetFeatureTermsParams params) {
		Session session = getSession();
		List<FeatureTerms> featureTerms = new ArrayList<FeatureTerms>();
		try{		
			StatementResult result = session.run( 
					"match(tx:Taxon{guid:{tguid}})--(f:Feature)"
					+ " optional match (f)--(t:GOTerm{space:{space}})"
					+ " return f.guid, t.guid"
					, parameters("tguid", params.getTaxonGuid(), 
							"space",params.getTermSpace()));
			
			while ( result.hasNext() )
			{
			    Record record = result.next();
			    
				List<String> termGuids = new ArrayList<String>();
				Value termGuid = record.get( "t.guid" );				
				if(!termGuid.isNull()){
					termGuids.add(termGuid.asString());
				}
				
			    featureTerms.add(new FeatureTerms()
			    		.withFeatureGuid(record.get( "f.guid" ).asString())
			    		.withTermGuids(termGuids));
			}
		}finally {
			session.close();
		}
		return featureTerms;
	}	
	
	public static void main(String[] args) throws IOException {
//		new Neo4jDataProvider(null).loadReferenceData();
		
//		new Neo4jDataProvider(null).cleanKEAppResults(new CleanKEAppResultsParams().withAppGuid("KEApp1"));
//		
//		String[][] _bis = new String[][]{
//			{"BIC:1", "CMP:1505431589321","KEApp1", "KBaseGen123;KBaseGen120;KBaseGen121"},
//			{"BIC:2","CMP:1505431589321","KEApp1", "KBaseGen101;KBaseGen100;KBaseGen104;KBaseGen111"},
//			{"BIC:3","CMP:1505431589339","KEApp1", "KBaseGen101;KBaseGen101;KBaseGen104;KBaseGen121"}
//		};
//		List<Bicluster> biclusters = new ArrayList<Bicluster>();
//		for(String[] vals: _bis){
//			biclusters.add(new Bicluster()
//					.withGuid(vals[0])
//					.withCompendiumGuid(vals[1])
//					.withKeappGuid(vals[2])
//					.withFeatureGuids(Arrays.asList(vals[3].split(";"))));			
//		}
//		new Neo4jDataProvider(null).storeBiclusters(new StoreBiclustersParams().withBiclusters(biclusters) );		

		
		
//		List<Bicluster> items = new Neo4jDataProvider(null).getBiclusters(new GetBiclustersParams().withBiclusterGuids(
//				Arrays.asList(new String[]{"BIC:1234", "BIC:3422"})
//				));
//		for(Bicluster b: items){
//			System.out.println(b);
//		}
		
//		List<CompendiumDescriptor> items = new Neo4jDataProvider(null).getCompendiumDescriptors(new GetCompendiumDescriptorsParams()
//				.withDataType("gene knockout fitness"));
////				.withDataType("gene expression"));
//		for(CompendiumDescriptor item: items){
//			System.out.println(item);
//		}

//		new Neo4jDataProvider(null).cleanKEAppResults(new CleanKEAppResultsParams().withAppGuid("KEApp1"));
//		
//		KEAppDescriptor res = new Neo4jDataProvider(null).getKEAppDescriptor(new GetKEAppDescriptorParams().withAppGuid("KEApp1"));
//		System.out.println(res);

		
//		GraphUpdateStat stat = new Neo4jDataProvider(null).storeWSGenome(new StoreWSGenomeParams()
//				.withFeatureGuids(Arrays.asList("ws:123/3/2:1","ws:123/3/2:2", "ws:123/3/2:3"))
//				.withGenomeRef("123/3/2")
//				);
//		System.out.println(stat);

		
//		String[] wsGuids = new String[]{"ws:123/3/2:1", "ws:123/3/2:2", "ws:123/3/2:3"};
//		String[] rfGuids = new String[]{"KBaseGen1313", "KBaseGen1312", "KBaseGen1314"};
//		Hashtable<String,String> mm = new Hashtable<String, String>();
//		for(int i = 0; i < wsGuids.length; i++){
//			mm.put(wsGuids[i], rfGuids[i]);
//		}
//		
//		GraphUpdateStat stat = new Neo4jDataProvider(null).connectWSFeatures2RefOrthologs(
//				new ConnectWSFeatures2RefOrthologsParams()
//				.withWs2refFeatureGuids(mm));
//		System.out.println(stat);
		
//		new Neo4jDataProvider(null).storeKEAppDescriptor(new StoreKEAppDescriptorParams()
//		.withApp(new KEAppDescriptor()
//				.withGuid("KEApp2")
//				.withLastRunEpoch(System.currentTimeMillis())
//				.withName("Expression Biclusters")
//				.withNodesCreated(1L)
//				.withPropertiesSet(1L)
//				.withRelationsCreated(1L)
//				.withVersion("1.0")
//		));			
		
//		GraphUpdateStat res = new Neo4jDataProvider(null).detachDelete("OrthologGroup", 1000, true);
//		System.out.println(res);
		
//		List<Bicluster> items = new Neo4jDataProvider(null).getBiclusters(new GetBiclustersParams()
////				.withTaxonomyGuid("KBaseTax3163472"));
////				.withCompendiumGuid("CMP:1505431589412")
//				.withKeappGuid("KEApp1"));
//		for(Bicluster b: items){
//			System.out.println(b); 
//		}
//		System.out.println("Items count: " + items.size());
		
//		List<FeatureTerms> items = new Neo4jDataProvider(null).getFeatureTerms(new GetFeatureTermsParams()
//				.withTaxonGuid("KBaseTax984557")
//				.withTermSpace("molecular_function"));
//		int fcount = 0;
//		int tcount = 0;
//		int hcount = 0;
//		HashSet<String> h = new HashSet<String>();
//		for(FeatureTerms item: items){
//			System.out.println(item);
//			fcount ++;
//			if(item.getTermGuids().size() > 0){
//				tcount ++;
//			}
//			if(h.contains(item.getFeatureGuid())){
//				hcount++;
//			}
//			h.add(item.getFeatureGuid());
//		}
//		System.out.println(" fcount = " + fcount 
//				+ "\t tcount = " + tcount 
//				+ "\t hcount = " + hcount);

//		new Neo4jDataProvider(null).storeKEAppDescriptor(new StoreKEAppDescriptorParams()
//		.withApp(new KEAppDescriptor()
//				.withGuid("KEApp2")
//				.withLastRunEpoch(System.currentTimeMillis())
//				.withName("Enrich GO terms")
//				.withNodesCreated(12342134L)
//				.withPropertiesSet(242342L)
//				.withRelationsCreated(145234L)
//				.withVersion("1.0")
//		));				
		
//		GraphUpdateStat stat = new Neo4jDataProvider(null).storeTermEnrichmentProfiles(
//				new StoreTermEnrichmentProfilesParams()
//				.withProfiles(Arrays.asList(
//						new TermEnrichmentProfile()
//						.withGuid("TE:1341234")
//						.withKeappGuid("KEApp2")
//						.withSourceGeneSetGuid("BIC:1505539382330_395")
//						.withSourceGeneSetType("Bicluster")
//						.withTermNamespace("molecular_function")
//						.withTerms(Arrays.asList(
//								new TermEnrichment()
//								.withExpectedCount(10L)
//								.withPValue(0.006)
//								.withSampleCount(4L)
//								.withTermGuid("GO:23423423")
//								.withTotalCount(145L)
//								,
//								new TermEnrichment()
//								.withExpectedCount(12L)
//								.withPValue(0.009)
//								.withSampleCount(3L)
//								.withTermGuid("GO:111")
//								.withTotalCount(200L)))						
//		)));	
//		System.out.println(stat);

		
		
//		String[] wsGuids = new String[]{"ws:111/3/2:1", "ws:111/3/2:2"};
//		String[] rfGuids = new String[]{"KBaseGen1313", "KBaseGen1312"};
//		Hashtable<String,String> mm = new Hashtable<String, String>();
//		for(int i = 0; i < wsGuids.length; i++){
//			mm.put(wsGuids[i], rfGuids[i]);
//		}
//		
//		GraphUpdateStat stat = new Neo4jDataProvider(null).storeRichWSGenome(new StoreRichWSGenomeParams()
//				.withGenomeRef("111/3/2")
//				.withWs2refFeatureGuids(mm)
//				.withFeatures(Arrays.asList(
//						new WSFeature()
//						.withGuid("ws:111/3/2:1")
//						.withName("name1")
//						.withRefTermGuid("GO:1231231")
//						
//						,new WSFeature()
//						.withGuid("ws:111/3/2:2")
//						.withName("name2")
//						
//						,new WSFeature()
//						.withGuid("ws:111/3/2:3")
//						.withName("name3")
//						.withRefTermGuid("GO:1231231")
//						
//						))
//		);
//		System.out.println(stat);		
		
//		List<Term> terms = new Neo4jDataProvider(null).getTerms(new GetTermsParams()
//				.withTermGuids(Arrays.asList("GO:0000001","GO:0000003")));
//		for(Term t: terms){
//			System.out.println(t);
//		}
		
		
//		GetWSFeatureTermEnrichmentProfilesOutput res = new Neo4jDataProvider(null).getWSFeatureTermEnrichmentProfiles(new GetWSFeatureTermEnrichmentProfilesParams()
//				.withWsFeatureGuid("ws:25582/31/1:feature/PGA1_RS02590")
//				.withKeappGuids(Arrays.asList("KEApp5","KEApp6","KEApp7")));
//		System.out.println(res);
		

		 List<WSFeatureTermPair> res = new Neo4jDataProvider(null).getWSFeatureTermPairs(new GetWSFeatureTermPairsParams()
				.withWsGenomeGuid("25582/31/1")
				.withTargetKeappGuid("_test"));
		System.out.println(res);
		
		
		
	}

	public GetWSFeatureTermEnrichmentProfilesOutput getWSFeatureTermEnrichmentProfiles(
			GetWSFeatureTermEnrichmentProfilesParams params) {
				
		GetWSFeatureTermEnrichmentProfilesOutput res = new GetWSFeatureTermEnrichmentProfilesOutput();
		Session session = getSession();
		try{			
			StatementResult result = session.run( 
					
					"match(f:WSFeature{guid:{fguid}})"
					+ "--(og:OrthologGroup)"
					+ "--(t:TermEnrichmentProfile)"
					+ "--(a:KEApp)"
					+ " where a.guid in {appGuids}"
					+ " return f.guid, f.name, f.ref_term_guid, t.guid, t.termSpace, t._appGuid, t.termGuids, t.pvalues",					
					parameters(
							"fguid", params.getWsFeatureGuid(),
							"appGuids", params.getKeappGuids()));
			
			HashSet<String> allTermGuids = new HashSet<String>();
			List<TermEnrichmentProfile> profiles = new ArrayList<TermEnrichmentProfile>();
			while ( result.hasNext() )
			{
			    Record record = result.next();
			    res
			    	.withFeatureGuid(record.get( "f.guid" ).asString())
			    	.withFeatureName(record.get( "f.name" ).asString())
			    	.withRefTermGuid(record.get( "f.ref_term_guid" ).isNull()? null: record.get( "f.ref_term_guid" ).asString())
			    	.withRefTermName(null)
			    	.withProfiles(null);
			
			    if(res.getRefTermGuid() != null){
			    	allTermGuids.add(res.getRefTermGuid());
			    }
			    			    			    
			    TermEnrichmentProfile profile = new TermEnrichmentProfile();
			    profile.withGuid(record.get( "t.guid" ).asString());
			    profile.withKeappGuid(record.get( "t._addGuid" ).asString());
			    profile.withTermNamespace(record.get( "t.termSpace" ).asString());
			    profile.withTerms(new ArrayList<TermEnrichment>());

			    List<String> termGuids = new ArrayList(record.get("t.termGuids").asList());			    
			    List<Double> pValues   = new ArrayList(record.get("t.pvalues").asList());
			    for(int i = 0; i < termGuids.size(); i++){
				    TermEnrichment te = new TermEnrichment();
				    te.withTermGuid(termGuids.get(i));
				    te.withPValue(pValues.get(i));
				    allTermGuids.add(te.getTermGuid());
				    profile.getTerms().add(te);
			    }
		    	profiles.add(profile);
			}	
			res.withProfiles(profiles);
			
			// Get GO Term names
			List<Term> terms = getTerms(new GetTermsParams()
					.withTermGuids( new ArrayList<String>(allTermGuids)));			
			Map<String, Term> guid2term = new Hashtable<String,Term>();
			for(Term term: terms){
				guid2term.put(term.getGuid(), term);
			}
			
			if(res.getRefTermGuid() != null){
				Term t = guid2term.get(res.getRefTermGuid());
				if(t != null){
					res.withRefTermName( t.getName())  ;
				}				
			}
			
			for( TermEnrichmentProfile p: res.getProfiles()){
				for(TermEnrichment te: p.getTerms() ){
					Term t = guid2term.get(te.getTermGuid());
					if(t != null){
						te.withTermName(t.getName());
					}
				}
			}
			
		}finally {
			session.close();
		}
		return res;
	}

	public List<WSFeatureTermPair> getWSFeatureTermPairs(GetWSFeatureTermPairsParams params) {
		List<WSFeatureTermPair> tps = new ArrayList<WSFeatureTermPair>();				
		Session session = getSession();
		try{			
			StatementResult result = session.run( 
					
					"match(g:WSGenome{guid:{gGuid}})"
					+ "--(f:WSFeature)"
					+ "--(og:OrthologGroup)"
					+ "--(t:TermEnrichmentProfile)"
					+ "--(a:KEApp{guid:{appGuid}})"
					+ " return f.guid, f.name, f.ref_term_guid, t.termGuids, t.pvalues",					
					parameters(
							"gGuid", params.getWsGenomeGuid(),
							"appGuid", params.getTargetKeappGuid()));
			
			HashSet<String> allTermGuids = new HashSet<String>();
			while ( result.hasNext() )
			{
			    Record record = result.next();
			    if( record.get( "f.ref_term_guid" ).isNull() ) continue;
			    
			    
			    List<String> termGuids = new ArrayList(record.get("t.termGuids").asList());			    
			    List<Double> pValues   = new ArrayList(record.get("t.pvalues").asList());
			    
			    String bestTermGuid = termGuids.get(0);
			    double bestPValue = pValues.get(0);
			    for(int i = 1; i < pValues.size(); i++){
			    	if(pValues.get(i) < bestPValue ){
			    		bestTermGuid = termGuids.get(i);
			    		bestPValue = pValues.get(i);
			    	}
			    }
			    
			    WSFeatureTermPair tp = new WSFeatureTermPair()
			    	.withFeatureGuid(record.get( "f.guid" ).asString())
			    	.withFeatureName(record.get( "f.name" ).asString())
			    	.withRefTermGuid(record.get( "f.ref_term_guid" ).isNull()? null: record.get( "f.ref_term_guid" ).asString())
			    	.withTargetTermGuid(bestTermGuid);
			    
			    tps.add(tp);
			    allTermGuids.add(tp.getRefTermGuid());
			    allTermGuids.add(tp.getTargetTermGuid());
			}	
			
			// Get GO Term names
			List<Term> terms = getTerms(new GetTermsParams()
					.withTermGuids( new ArrayList<String>(allTermGuids)));			
			Map<String, Term> guid2term = new Hashtable<String,Term>();
			for(Term term: terms){
				guid2term.put(term.getGuid(), term);
			}

			for( WSFeatureTermPair tp: tps){
				Term t;
				t = guid2term.get(tp.getRefTermGuid());
				if(t != null){
					tp.setRefTermName(t.getName()); 
				}
				
				t = guid2term.get(tp.getTargetTermGuid());
				if(t != null){
					tp.setTargetTermName(t.getName()); 
				}
			}
			
		}finally {
			session.close();
		}
		return tps;
	}

	public List<Term> getTerms(GetTermsParams params) {
		List<Term> terms = new ArrayList<Term>();
		Session session = getSession();
		try{			
			StatementResult result = session.run( 					
					"match(t:GOTerm) where t.guid in {tguids} "
					+ " return t.guid, t.name, t.space",
					parameters(
							"tguids", params.getTermGuids()));
			
			while ( result.hasNext() )
			{
			    Record record = result.next();
			    terms.add(new Term()
			    		.withGuid(record.get( "t.guid" ).asString())
			    		.withName(record.get( "t.name" ).asString())
			    		.withSpace(record.get( "t.space" ).asString()));
			}	
		}finally {
			session.close();
		}
		return terms;
	}

}
