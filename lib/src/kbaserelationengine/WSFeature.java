
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
 * <p>Original spec-file type: WSFeature</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "guid",
    "name",
    "function",
    "aliases",
    "ref_term_guid"
})
public class WSFeature {

    @JsonProperty("guid")
    private java.lang.String guid;
    @JsonProperty("name")
    private java.lang.String name;
    @JsonProperty("function")
    private java.lang.String function;
    @JsonProperty("aliases")
    private List<String> aliases;
    @JsonProperty("ref_term_guid")
    private java.lang.String refTermGuid;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("guid")
    public java.lang.String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(java.lang.String guid) {
        this.guid = guid;
    }

    public WSFeature withGuid(java.lang.String guid) {
        this.guid = guid;
        return this;
    }

    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public WSFeature withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("function")
    public java.lang.String getFunction() {
        return function;
    }

    @JsonProperty("function")
    public void setFunction(java.lang.String function) {
        this.function = function;
    }

    public WSFeature withFunction(java.lang.String function) {
        this.function = function;
        return this;
    }

    @JsonProperty("aliases")
    public List<String> getAliases() {
        return aliases;
    }

    @JsonProperty("aliases")
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public WSFeature withAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    @JsonProperty("ref_term_guid")
    public java.lang.String getRefTermGuid() {
        return refTermGuid;
    }

    @JsonProperty("ref_term_guid")
    public void setRefTermGuid(java.lang.String refTermGuid) {
        this.refTermGuid = refTermGuid;
    }

    public WSFeature withRefTermGuid(java.lang.String refTermGuid) {
        this.refTermGuid = refTermGuid;
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
        return ((((((((((((("WSFeature"+" [guid=")+ guid)+", name=")+ name)+", function=")+ function)+", aliases=")+ aliases)+", refTermGuid=")+ refTermGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
