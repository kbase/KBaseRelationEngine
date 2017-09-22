
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
 * <p>Original spec-file type: WSFeature</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "guid",
    "name",
    "ref_term_guid"
})
public class WSFeature {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ref_term_guid")
    private String refTermGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public WSFeature withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public WSFeature withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("ref_term_guid")
    public String getRefTermGuid() {
        return refTermGuid;
    }

    @JsonProperty("ref_term_guid")
    public void setRefTermGuid(String refTermGuid) {
        this.refTermGuid = refTermGuid;
    }

    public WSFeature withRefTermGuid(String refTermGuid) {
        this.refTermGuid = refTermGuid;
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
        return ((((((((("WSFeature"+" [guid=")+ guid)+", name=")+ name)+", refTermGuid=")+ refTermGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
