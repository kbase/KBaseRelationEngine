
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
 * <p>Original spec-file type: GetTermsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "term_guids"
})
public class GetTermsParams {

    @JsonProperty("term_guids")
    private List<String> termGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("term_guids")
    public List<String> getTermGuids() {
        return termGuids;
    }

    @JsonProperty("term_guids")
    public void setTermGuids(List<String> termGuids) {
        this.termGuids = termGuids;
    }

    public GetTermsParams withTermGuids(List<String> termGuids) {
        this.termGuids = termGuids;
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
        return ((((("GetTermsParams"+" [termGuids=")+ termGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
