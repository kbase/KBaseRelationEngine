
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
 * <p>Original spec-file type: GetOrthologGroupsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "with_term_enrichmnet_profiles",
    "app_guid"
})
public class GetOrthologGroupsParams {

    @JsonProperty("with_term_enrichmnet_profiles")
    private Long withTermEnrichmnetProfiles;
    @JsonProperty("app_guid")
    private String appGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("with_term_enrichmnet_profiles")
    public Long getWithTermEnrichmnetProfiles() {
        return withTermEnrichmnetProfiles;
    }

    @JsonProperty("with_term_enrichmnet_profiles")
    public void setWithTermEnrichmnetProfiles(Long withTermEnrichmnetProfiles) {
        this.withTermEnrichmnetProfiles = withTermEnrichmnetProfiles;
    }

    public GetOrthologGroupsParams withWithTermEnrichmnetProfiles(Long withTermEnrichmnetProfiles) {
        this.withTermEnrichmnetProfiles = withTermEnrichmnetProfiles;
        return this;
    }

    @JsonProperty("app_guid")
    public String getAppGuid() {
        return appGuid;
    }

    @JsonProperty("app_guid")
    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public GetOrthologGroupsParams withAppGuid(String appGuid) {
        this.appGuid = appGuid;
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
        return ((((((("GetOrthologGroupsParams"+" [withTermEnrichmnetProfiles=")+ withTermEnrichmnetProfiles)+", appGuid=")+ appGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
