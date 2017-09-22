
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
 * <p>Original spec-file type: Term</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "guid",
    "name",
    "space"
})
public class Term {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("space")
    private String space;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Term withGuid(String guid) {
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

    public Term withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("space")
    public String getSpace() {
        return space;
    }

    @JsonProperty("space")
    public void setSpace(String space) {
        this.space = space;
    }

    public Term withSpace(String space) {
        this.space = space;
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
        return ((((((((("Term"+" [guid=")+ guid)+", name=")+ name)+", space=")+ space)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
