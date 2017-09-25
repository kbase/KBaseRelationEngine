package kbaserelationengine;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
//END_HEADER

/**
 * <p>Original spec-file module name: KBaseRelationEngine</p>
 * <pre>
 * A KBase module: KBaseRelationEngine
 * </pre>
 */
public class KBaseRelationEngineServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;
    private static final String version = "0.0.1";
    private static final String gitUrl = "https://github.com/psnovichkov/KBaseRelationEngine.git";
    private static final String gitCommitHash = "66c406e170c2c7dcb18c9a296a61d458fbb73330";

    //BEGIN_CLASS_HEADER
    Set<String> admins  = new HashSet<String>();
    Neo4jDataProvider dataProvider;
    
    private void checkAdmin(AuthToken authPart) {
    	if( !admins.contains(authPart.getUserName()) ){
    		new IllegalStateException("User " + authPart.getUserName() + " should be admin to perform this operation");
    	}
	}
    
    //END_CLASS_HEADER

    public KBaseRelationEngineServer() throws Exception {
        super("KBaseRelationEngine");
        //BEGIN_CONSTRUCTOR
        admins.addAll(Arrays.asList(config.get("admins").split(",")));
        dataProvider = new Neo4jDataProvider(config);
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: getFeatureSequences</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetFeatureSequencesParams GetFeatureSequencesParams}
     * @return   instance of list of type {@link kbaserelationengine.FeatureSequence FeatureSequence}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getFeatureSequences", async=true)
    public List<FeatureSequence> getFeatureSequences(GetFeatureSequencesParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<FeatureSequence> returnVal = null;
        //BEGIN getFeatureSequences
        returnVal = dataProvider.getFeatureSequences(params);
        //END getFeatureSequences
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getCompendiumDescriptors</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetCompendiumDescriptorsParams GetCompendiumDescriptorsParams}
     * @return   instance of list of type {@link kbaserelationengine.CompendiumDescriptor CompendiumDescriptor}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getCompendiumDescriptors", async=true)
    public List<CompendiumDescriptor> getCompendiumDescriptors(GetCompendiumDescriptorsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<CompendiumDescriptor> returnVal = null;
        //BEGIN getCompendiumDescriptors
        returnVal = dataProvider.getCompendiumDescriptors(params);
        //END getCompendiumDescriptors
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: storeKEAppDescriptor</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreKEAppDescriptorParams StoreKEAppDescriptorParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.storeKEAppDescriptor", async=true)
    public GraphUpdateStat storeKEAppDescriptor(StoreKEAppDescriptorParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GraphUpdateStat returnVal = null;
        //BEGIN storeKEAppDescriptor
    	checkAdmin(authPart);
    	returnVal = dataProvider.storeKEAppDescriptor(params);
        //END storeKEAppDescriptor
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: cleanKEAppResults</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.CleanKEAppResultsParams CleanKEAppResultsParams}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.cleanKEAppResults", async=true)
    public void cleanKEAppResults(CleanKEAppResultsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN cleanKEAppResults
    	checkAdmin(authPart);
    	dataProvider.cleanKEAppResults(params);
        //END cleanKEAppResults
    }

    /**
     * <p>Original spec-file function name: getKEAppDescriptor</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetKEAppDescriptorParams GetKEAppDescriptorParams}
     * @return   instance of type {@link kbaserelationengine.KEAppDescriptor KEAppDescriptor}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getKEAppDescriptor", async=true)
    public KEAppDescriptor getKEAppDescriptor(GetKEAppDescriptorParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        KEAppDescriptor returnVal = null;
        //BEGIN getKEAppDescriptor
        returnVal = dataProvider.getKEAppDescriptor(params);
        //END getKEAppDescriptor
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: storeBiclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreBiclustersParams StoreBiclustersParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.storeBiclusters", async=true)
    public GraphUpdateStat storeBiclusters(StoreBiclustersParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GraphUpdateStat returnVal = null;
        //BEGIN storeBiclusters
    	checkAdmin(authPart);    	
    	returnVal = dataProvider.storeBiclusters(params);
        //END storeBiclusters
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getBiclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetBiclustersParams GetBiclustersParams}
     * @return   instance of list of type {@link kbaserelationengine.Bicluster Bicluster}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getBiclusters", async=true)
    public List<Bicluster> getBiclusters(GetBiclustersParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<Bicluster> returnVal = null;
        //BEGIN getBiclusters
        returnVal = dataProvider.getBiclusters(params);        
        //END getBiclusters
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: storeTermEnrichmentProfiles</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreTermEnrichmentProfilesParams StoreTermEnrichmentProfilesParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.storeTermEnrichmentProfiles", async=true)
    public GraphUpdateStat storeTermEnrichmentProfiles(StoreTermEnrichmentProfilesParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GraphUpdateStat returnVal = null;
        //BEGIN storeTermEnrichmentProfiles
    	checkAdmin(authPart);
        returnVal = dataProvider.storeTermEnrichmentProfiles(params);        
        //END storeTermEnrichmentProfiles
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getWSFeatureTermEnrichmentProfiles</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetWSFeatureTermEnrichmentProfilesParams GetWSFeatureTermEnrichmentProfilesParams}
     * @return   instance of type {@link kbaserelationengine.GetWSFeatureTermEnrichmentProfilesOutput GetWSFeatureTermEnrichmentProfilesOutput}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getWSFeatureTermEnrichmentProfiles", async=true)
    public GetWSFeatureTermEnrichmentProfilesOutput getWSFeatureTermEnrichmentProfiles(GetWSFeatureTermEnrichmentProfilesParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GetWSFeatureTermEnrichmentProfilesOutput returnVal = null;
        //BEGIN getWSFeatureTermEnrichmentProfiles
    	returnVal = dataProvider.getWSFeatureTermEnrichmentProfiles(params);                                
        //END getWSFeatureTermEnrichmentProfiles
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getWSFeatureTermPairs</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetWSFeatureTermPairsParams GetWSFeatureTermPairsParams}
     * @return   instance of list of type {@link kbaserelationengine.WSFeatureTermPair WSFeatureTermPair}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getWSFeatureTermPairs", async=true)
    public List<WSFeatureTermPair> getWSFeatureTermPairs(GetWSFeatureTermPairsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<WSFeatureTermPair> returnVal = null;
        //BEGIN getWSFeatureTermPairs
    	returnVal = dataProvider.getWSFeatureTermPairs(params);                        
        //END getWSFeatureTermPairs
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getFeatureTerms</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetFeatureTermsParams GetFeatureTermsParams}
     * @return   instance of list of type {@link kbaserelationengine.FeatureTerms FeatureTerms}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getFeatureTerms", async=true)
    public List<FeatureTerms> getFeatureTerms(GetFeatureTermsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<FeatureTerms> returnVal = null;
        //BEGIN getFeatureTerms
        returnVal = dataProvider.getFeatureTerms(params);
        //END getFeatureTerms
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getTerms</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetTermsParams GetTermsParams}
     * @return   instance of list of type {@link kbaserelationengine.Term Term}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getTerms", async=true)
    public List<Term> getTerms(GetTermsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<Term> returnVal = null;
        //BEGIN getTerms
        returnVal = dataProvider.getTerms(params);
        //END getTerms
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getOrthologGroups</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetOrthologGroupsParams GetOrthologGroupsParams}
     * @return   instance of type {@link kbaserelationengine.GetOrthologGroupsOutput GetOrthologGroupsOutput}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getOrthologGroups", async=true)
    public GetOrthologGroupsOutput getOrthologGroups(GetOrthologGroupsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GetOrthologGroupsOutput returnVal = null;
        //BEGIN getOrthologGroups
    	returnVal = dataProvider.getOrthologGroups(params);                                
        //END getOrthologGroups
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getOrthologTermEnrichmentProfiles</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetOrthologTermEnrichmentProfilesParams GetOrthologTermEnrichmentProfilesParams}
     * @return   instance of type {@link kbaserelationengine.GetOrthologTermEnrichmentProfilesOutput GetOrthologTermEnrichmentProfilesOutput}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getOrthologTermEnrichmentProfiles", async=true)
    public GetOrthologTermEnrichmentProfilesOutput getOrthologTermEnrichmentProfiles(GetOrthologTermEnrichmentProfilesParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GetOrthologTermEnrichmentProfilesOutput returnVal = null;
        //BEGIN getOrthologTermEnrichmentProfiles
    	returnVal = dataProvider.getOrthologTermEnrichmentProfiles(params);                                        
        //END getOrthologTermEnrichmentProfiles
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: storeWSGenome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreWSGenomeParams StoreWSGenomeParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.storeWSGenome", async=true)
    public GraphUpdateStat storeWSGenome(StoreWSGenomeParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GraphUpdateStat returnVal = null;
        //BEGIN storeWSGenome
    	checkAdmin(authPart);
        returnVal = dataProvider.storeWSGenome(params);        
        //END storeWSGenome
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: storeRichWSGenome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreRichWSGenomeParams StoreRichWSGenomeParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.storeRichWSGenome", async=true)
    public GraphUpdateStat storeRichWSGenome(StoreRichWSGenomeParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GraphUpdateStat returnVal = null;
        //BEGIN storeRichWSGenome
    	checkAdmin(authPart);
    	returnVal = dataProvider.storeRichWSGenome(params);                
        //END storeRichWSGenome
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: connectWSFeatures2RefOrthologs</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.ConnectWSFeatures2RefOrthologsParams ConnectWSFeatures2RefOrthologsParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.connectWSFeatures2RefOrthologs", async=true)
    public GraphUpdateStat connectWSFeatures2RefOrthologs(ConnectWSFeatures2RefOrthologsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GraphUpdateStat returnVal = null;
        //BEGIN connectWSFeatures2RefOrthologs
    	checkAdmin(authPart);
        returnVal = dataProvider.connectWSFeatures2RefOrthologs(params);                
        //END connectWSFeatures2RefOrthologs
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: connectWSFeatures2RefOTerms</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.ConnectWSFeatures2RefOTermsParams ConnectWSFeatures2RefOTermsParams}
     * @return   instance of type {@link kbaserelationengine.GraphUpdateStat GraphUpdateStat}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.connectWSFeatures2RefOTerms", async=true)
    public GraphUpdateStat connectWSFeatures2RefOTerms(ConnectWSFeatures2RefOTermsParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        GraphUpdateStat returnVal = null;
        //BEGIN connectWSFeatures2RefOTerms
    	checkAdmin(authPart);
        returnVal = dataProvider.connectWSFeatures2RefOTerms(params);                        
        //END connectWSFeatures2RefOTerms
        return returnVal;
    }
    @JsonServerMethod(rpc = "KBaseRelationEngine.status")
    public Map<String, Object> status() {
        Map<String, Object> returnVal = null;
        //BEGIN_STATUS
        returnVal = new LinkedHashMap<String, Object>();
        returnVal.put("state", "OK");
        returnVal.put("message", "");
        returnVal.put("version", version);
        returnVal.put("git_url", gitUrl);
        returnVal.put("git_commit_hash", gitCommitHash);
        //END_STATUS
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new KBaseRelationEngineServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new KBaseRelationEngineServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
