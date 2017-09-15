
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
 * <p>Original spec-file type: GetKEAppDescriptorParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "appGuid"
})
public class GetKEAppDescriptorParams {

    @JsonProperty("appGuid")
    private String appGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("appGuid")
    public String getAppGuid() {
        return appGuid;
    }

    @JsonProperty("appGuid")
    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public GetKEAppDescriptorParams withAppGuid(String appGuid) {
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
        return ((((("GetKEAppDescriptorParams"+" [appGuid=")+ appGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
