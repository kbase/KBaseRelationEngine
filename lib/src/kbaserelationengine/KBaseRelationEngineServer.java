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
    private static final String gitCommitHash = "9825c61eb6fcb89a2ae4bff29d5f103abc12b81d";

    //BEGIN_CLASS_HEADER
    //END_CLASS_HEADER

    public KBaseRelationEngineServer() throws Exception {
        super("KBaseRelationEngine");
        //BEGIN_CONSTRUCTOR
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: initReferenceData</p>
     * <pre>
     * </pre>
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.initReferenceData", async=true)
    public void initReferenceData(AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN initReferenceData
        //END initReferenceData
    }

    /**
     * <p>Original spec-file function name: getFeatureSequences</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.GetFeatureSequencesParams GetFeatureSequencesParams}
     * @return   instance of list of type {@link kbaserelationengine.FeatureSequence FeatureSequence}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.getFeatureSequences", async=true)
    public List<FeatureSequence> getFeatureSequences(GetFeatureSequencesParams params, RpcContext jsonRpcContext) throws Exception {
        List<FeatureSequence> returnVal = null;
        //BEGIN getFeatureSequences
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
    public List<CompendiumDescriptor> getCompendiumDescriptors(GetCompendiumDescriptorsParams params, RpcContext jsonRpcContext) throws Exception {
        List<CompendiumDescriptor> returnVal = null;
        //BEGIN getCompendiumDescriptors
        //END getCompendiumDescriptors
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: storeBiclusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kbaserelationengine.StoreBiclustersParams StoreBiclustersParams}
     */
    @JsonServerMethod(rpc = "KBaseRelationEngine.storeBiclusters", async=true)
    public void storeBiclusters(StoreBiclustersParams params, RpcContext jsonRpcContext) throws Exception {
        //BEGIN storeBiclusters
        //END storeBiclusters
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
