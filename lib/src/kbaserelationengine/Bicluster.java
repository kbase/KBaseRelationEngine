
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
 * <p>Original spec-file type: Bicluster</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "keapp_guid",
    "compendium_guid",
    "feature_guids",
    "condition_guids"
})
public class Bicluster {

    @JsonProperty("keapp_guid")
    private java.lang.String keappGuid;
    @JsonProperty("compendium_guid")
    private java.lang.String compendiumGuid;
    @JsonProperty("feature_guids")
    private List<String> featureGuids;
    @JsonProperty("condition_guids")
    private List<String> conditionGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("keapp_guid")
    public java.lang.String getKeappGuid() {
        return keappGuid;
    }

    @JsonProperty("keapp_guid")
    public void setKeappGuid(java.lang.String keappGuid) {
        this.keappGuid = keappGuid;
    }

    public Bicluster withKeappGuid(java.lang.String keappGuid) {
        this.keappGuid = keappGuid;
        return this;
    }

    @JsonProperty("compendium_guid")
    public java.lang.String getCompendiumGuid() {
        return compendiumGuid;
    }

    @JsonProperty("compendium_guid")
    public void setCompendiumGuid(java.lang.String compendiumGuid) {
        this.compendiumGuid = compendiumGuid;
    }

    public Bicluster withCompendiumGuid(java.lang.String compendiumGuid) {
        this.compendiumGuid = compendiumGuid;
        return this;
    }

    @JsonProperty("feature_guids")
    public List<String> getFeatureGuids() {
        return featureGuids;
    }

    @JsonProperty("feature_guids")
    public void setFeatureGuids(List<String> featureGuids) {
        this.featureGuids = featureGuids;
    }

    public Bicluster withFeatureGuids(List<String> featureGuids) {
        this.featureGuids = featureGuids;
        return this;
    }

    @JsonProperty("condition_guids")
    public List<String> getConditionGuids() {
        return conditionGuids;
    }

    @JsonProperty("condition_guids")
    public void setConditionGuids(List<String> conditionGuids) {
        this.conditionGuids = conditionGuids;
    }

    public Bicluster withConditionGuids(List<String> conditionGuids) {
        this.conditionGuids = conditionGuids;
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
        return ((((((((((("Bicluster"+" [keappGuid=")+ keappGuid)+", compendiumGuid=")+ compendiumGuid)+", featureGuids=")+ featureGuids)+", conditionGuids=")+ conditionGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
