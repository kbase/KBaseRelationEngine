
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
 * <p>Original spec-file type: GetOrthologTermEnrichmentProfilesOutput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "ortholog2profiles"
})
public class GetOrthologTermEnrichmentProfilesOutput {

    @JsonProperty("ortholog2profiles")
    private Map<String, List<TermEnrichmentProfile>> ortholog2profiles;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("ortholog2profiles")
    public Map<String, List<TermEnrichmentProfile>> getOrtholog2profiles() {
        return ortholog2profiles;
    }

    @JsonProperty("ortholog2profiles")
    public void setOrtholog2profiles(Map<String, List<TermEnrichmentProfile>> ortholog2profiles) {
        this.ortholog2profiles = ortholog2profiles;
    }

    public GetOrthologTermEnrichmentProfilesOutput withOrtholog2profiles(Map<String, List<TermEnrichmentProfile>> ortholog2profiles) {
        this.ortholog2profiles = ortholog2profiles;
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
        return ((((("GetOrthologTermEnrichmentProfilesOutput"+" [ortholog2profiles=")+ ortholog2profiles)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
