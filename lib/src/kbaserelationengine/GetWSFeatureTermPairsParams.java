
package kbaserelationengine;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: GetWSFeatureTermPairsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ws_genome_guid",
    "target_keapp_guid"
})
public class GetWSFeatureTermPairsParams {

    @JsonProperty("ws_genome_guid")
    private String wsGenomeGuid;
    @JsonProperty("target_keapp_guid")
    private String targetKeappGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ws_genome_guid")
    public String getWsGenomeGuid() {
        return wsGenomeGuid;
    }

    @JsonProperty("ws_genome_guid")
    public void setWsGenomeGuid(String wsGenomeGuid) {
        this.wsGenomeGuid = wsGenomeGuid;
    }

    public GetWSFeatureTermPairsParams withWsGenomeGuid(String wsGenomeGuid) {
        this.wsGenomeGuid = wsGenomeGuid;
        return this;
    }

    @JsonProperty("target_keapp_guid")
    public String getTargetKeappGuid() {
        return targetKeappGuid;
    }

    @JsonProperty("target_keapp_guid")
    public void setTargetKeappGuid(String targetKeappGuid) {
        this.targetKeappGuid = targetKeappGuid;
    }

    public GetWSFeatureTermPairsParams withTargetKeappGuid(String targetKeappGuid) {
        this.targetKeappGuid = targetKeappGuid;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((((("GetWSFeatureTermPairsParams"+" [wsGenomeGuid=")+ wsGenomeGuid)+", targetKeappGuid=")+ targetKeappGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
