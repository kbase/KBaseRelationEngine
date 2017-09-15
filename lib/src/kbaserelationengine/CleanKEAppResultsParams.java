
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
 * <p>Original spec-file type: CleanKEAppResultsParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "appGuid"
})
public class CleanKEAppResultsParams {

    @JsonProperty("appGuid")
    private Long appGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("appGuid")
    public Long getAppGuid() {
        return appGuid;
    }

    @JsonProperty("appGuid")
    public void setAppGuid(Long appGuid) {
        this.appGuid = appGuid;
    }

    public CleanKEAppResultsParams withAppGuid(Long appGuid) {
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
        return ((((("CleanKEAppResultsParams"+" [appGuid=")+ appGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
