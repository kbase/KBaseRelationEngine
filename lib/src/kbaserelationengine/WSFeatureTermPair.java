
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
 * <p>Original spec-file type: WSFeatureTermPair</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "feature_guid",
    "feature_name",
    "ref_term_guid",
    "ref_term_name",
    "target_term_guid",
    "target_term_name"
})
public class WSFeatureTermPair {

    @JsonProperty("feature_guid")
    private String featureGuid;
    @JsonProperty("feature_name")
    private String featureName;
    @JsonProperty("ref_term_guid")
    private String refTermGuid;
    @JsonProperty("ref_term_name")
    private String refTermName;
    @JsonProperty("target_term_guid")
    private String targetTermGuid;
    @JsonProperty("target_term_name")
    private String targetTermName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("feature_guid")
    public String getFeatureGuid() {
        return featureGuid;
    }

    @JsonProperty("feature_guid")
    public void setFeatureGuid(String featureGuid) {
        this.featureGuid = featureGuid;
    }

    public WSFeatureTermPair withFeatureGuid(String featureGuid) {
        this.featureGuid = featureGuid;
        return this;
    }

    @JsonProperty("feature_name")
    public String getFeatureName() {
        return featureName;
    }

    @JsonProperty("feature_name")
    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public WSFeatureTermPair withFeatureName(String featureName) {
        this.featureName = featureName;
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

    public WSFeatureTermPair withRefTermGuid(String refTermGuid) {
        this.refTermGuid = refTermGuid;
        return this;
    }

    @JsonProperty("ref_term_name")
    public String getRefTermName() {
        return refTermName;
    }

    @JsonProperty("ref_term_name")
    public void setRefTermName(String refTermName) {
        this.refTermName = refTermName;
    }

    public WSFeatureTermPair withRefTermName(String refTermName) {
        this.refTermName = refTermName;
        return this;
    }

    @JsonProperty("target_term_guid")
    public String getTargetTermGuid() {
        return targetTermGuid;
    }

    @JsonProperty("target_term_guid")
    public void setTargetTermGuid(String targetTermGuid) {
        this.targetTermGuid = targetTermGuid;
    }

    public WSFeatureTermPair withTargetTermGuid(String targetTermGuid) {
        this.targetTermGuid = targetTermGuid;
        return this;
    }

    @JsonProperty("target_term_name")
    public String getTargetTermName() {
        return targetTermName;
    }

    @JsonProperty("target_term_name")
    public void setTargetTermName(String targetTermName) {
        this.targetTermName = targetTermName;
    }

    public WSFeatureTermPair withTargetTermName(String targetTermName) {
        this.targetTermName = targetTermName;
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
        return ((((((((((((((("WSFeatureTermPair"+" [featureGuid=")+ featureGuid)+", featureName=")+ featureName)+", refTermGuid=")+ refTermGuid)+", refTermName=")+ refTermName)+", targetTermGuid=")+ targetTermGuid)+", targetTermName=")+ targetTermName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
