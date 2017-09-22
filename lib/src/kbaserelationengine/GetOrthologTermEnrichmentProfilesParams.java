
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
 * <p>Original spec-file type: GetOrthologTermEnrichmentProfilesParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ortholog_group_guids",
    "app_guid"
})
public class GetOrthologTermEnrichmentProfilesParams {

    @JsonProperty("ortholog_group_guids")
    private List<String> orthologGroupGuids;
    @JsonProperty("app_guid")
    private java.lang.String appGuid;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("ortholog_group_guids")
    public List<String> getOrthologGroupGuids() {
        return orthologGroupGuids;
    }

    @JsonProperty("ortholog_group_guids")
    public void setOrthologGroupGuids(List<String> orthologGroupGuids) {
        this.orthologGroupGuids = orthologGroupGuids;
    }

    public GetOrthologTermEnrichmentProfilesParams withOrthologGroupGuids(List<String> orthologGroupGuids) {
        this.orthologGroupGuids = orthologGroupGuids;
        return this;
    }

    @JsonProperty("app_guid")
    public java.lang.String getAppGuid() {
        return appGuid;
    }

    @JsonProperty("app_guid")
    public void setAppGuid(java.lang.String appGuid) {
        this.appGuid = appGuid;
    }

    public GetOrthologTermEnrichmentProfilesParams withAppGuid(java.lang.String appGuid) {
        this.appGuid = appGuid;
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
        return ((((((("GetOrthologTermEnrichmentProfilesParams"+" [orthologGroupGuids=")+ orthologGroupGuids)+", appGuid=")+ appGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
