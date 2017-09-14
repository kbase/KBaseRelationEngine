
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
 * <p>Original spec-file type: BiclusterDescriptor</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "guid",
    "keapp_guid",
    "compendium_guid"
})
public class BiclusterDescriptor {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("keapp_guid")
    private String keappGuid;
    @JsonProperty("compendium_guid")
    private String compendiumGuid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public BiclusterDescriptor withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    @JsonProperty("keapp_guid")
    public String getKeappGuid() {
        return keappGuid;
    }

    @JsonProperty("keapp_guid")
    public void setKeappGuid(String keappGuid) {
        this.keappGuid = keappGuid;
    }

    public BiclusterDescriptor withKeappGuid(String keappGuid) {
        this.keappGuid = keappGuid;
        return this;
    }

    @JsonProperty("compendium_guid")
    public String getCompendiumGuid() {
        return compendiumGuid;
    }

    @JsonProperty("compendium_guid")
    public void setCompendiumGuid(String compendiumGuid) {
        this.compendiumGuid = compendiumGuid;
    }

    public BiclusterDescriptor withCompendiumGuid(String compendiumGuid) {
        this.compendiumGuid = compendiumGuid;
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
        return ((((((((("BiclusterDescriptor"+" [guid=")+ guid)+", keappGuid=")+ keappGuid)+", compendiumGuid=")+ compendiumGuid)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
