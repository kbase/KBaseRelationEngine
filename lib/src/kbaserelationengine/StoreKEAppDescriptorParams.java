
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
 * <p>Original spec-file type: StoreKEAppDescriptorParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "keapp"
})
public class StoreKEAppDescriptorParams {

    /**
     * <p>Original spec-file type: KEAppDescriptor</p>
     * 
     * 
     */
    @JsonProperty("keapp")
    private KEAppDescriptor keapp;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * <p>Original spec-file type: KEAppDescriptor</p>
     * 
     * 
     */
    @JsonProperty("keapp")
    public KEAppDescriptor getKeapp() {
        return keapp;
    }

    /**
     * <p>Original spec-file type: KEAppDescriptor</p>
     * 
     * 
     */
    @JsonProperty("keapp")
    public void setKeapp(KEAppDescriptor keapp) {
        this.keapp = keapp;
    }

    public StoreKEAppDescriptorParams withKeapp(KEAppDescriptor keapp) {
        this.keapp = keapp;
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
        return ((((("StoreKEAppDescriptorParams"+" [keapp=")+ keapp)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
