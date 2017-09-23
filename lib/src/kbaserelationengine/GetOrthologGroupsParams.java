
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
 * <p>Original spec-file type: GetOrthologGroupsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "with_term_enrichmnet_profiles",
    "app_guids"
})
public class GetOrthologGroupsParams {

    @JsonProperty("with_term_enrichmnet_profiles")
    private Long withTermEnrichmnetProfiles;
    @JsonProperty("app_guids")
    private List<String> appGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

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

    @JsonProperty("app_guids")
    public List<String> getAppGuids() {
        return appGuids;
    }

    @JsonProperty("app_guids")
    public void setAppGuids(List<String> appGuids) {
        this.appGuids = appGuids;
    }

    public GetOrthologGroupsParams withAppGuids(List<String> appGuids) {
        this.appGuids = appGuids;
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
        return ((((((("GetOrthologGroupsParams"+" [withTermEnrichmnetProfiles=")+ withTermEnrichmnetProfiles)+", appGuids=")+ appGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
