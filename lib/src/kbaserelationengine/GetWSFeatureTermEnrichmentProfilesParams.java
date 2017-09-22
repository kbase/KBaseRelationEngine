
package kbaserelationengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: GetWSFeatureTermEnrichmentProfilesParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ws_feature_guid",
    "ortholog_profiles",
    "keapp_guids"
})
public class GetWSFeatureTermEnrichmentProfilesParams {

    @JsonProperty("ws_feature_guid")
    private java.lang.String wsFeatureGuid;
    @JsonProperty("ortholog_profiles")
    private Long orthologProfiles;
    @JsonProperty("keapp_guids")
    private List<String> keappGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("ws_feature_guid")
    public java.lang.String getWsFeatureGuid() {
        return wsFeatureGuid;
    }

    @JsonProperty("ws_feature_guid")
    public void setWsFeatureGuid(java.lang.String wsFeatureGuid) {
        this.wsFeatureGuid = wsFeatureGuid;
    }

    public GetWSFeatureTermEnrichmentProfilesParams withWsFeatureGuid(java.lang.String wsFeatureGuid) {
        this.wsFeatureGuid = wsFeatureGuid;
        return this;
    }

    @JsonProperty("ortholog_profiles")
    public Long getOrthologProfiles() {
        return orthologProfiles;
    }

    @JsonProperty("ortholog_profiles")
    public void setOrthologProfiles(Long orthologProfiles) {
        this.orthologProfiles = orthologProfiles;
    }

    public GetWSFeatureTermEnrichmentProfilesParams withOrthologProfiles(Long orthologProfiles) {
        this.orthologProfiles = orthologProfiles;
        return this;
    }

    @JsonProperty("keapp_guids")
    public List<String> getKeappGuids() {
        return keappGuids;
    }

    @JsonProperty("keapp_guids")
    public void setKeappGuids(List<String> keappGuids) {
        this.keappGuids = keappGuids;
    }

    public GetWSFeatureTermEnrichmentProfilesParams withKeappGuids(List<String> keappGuids) {
        this.keappGuids = keappGuids;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((((((("GetWSFeatureTermEnrichmentProfilesParams"+" [wsFeatureGuid=")+ wsFeatureGuid)+", orthologProfiles=")+ orthologProfiles)+", keappGuids=")+ keappGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
