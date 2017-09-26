
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
 * <p>Original spec-file type: WSFeatureTermPair</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "feature_guid",
    "feature_name",
    "feature_function",
    "feature_aliases",
    "with_expression",
    "with_fitness",
    "ref_term_guid",
    "ref_term_name",
    "target_term_guid",
    "target_term_name"
})
public class WSFeatureTermPair {

    @JsonProperty("feature_guid")
    private java.lang.String featureGuid;
    @JsonProperty("feature_name")
    private java.lang.String featureName;
    @JsonProperty("feature_function")
    private java.lang.String featureFunction;
    @JsonProperty("feature_aliases")
    private List<String> featureAliases;
    @JsonProperty("with_expression")
    private Long withExpression;
    @JsonProperty("with_fitness")
    private Long withFitness;
    @JsonProperty("ref_term_guid")
    private java.lang.String refTermGuid;
    @JsonProperty("ref_term_name")
    private java.lang.String refTermName;
    @JsonProperty("target_term_guid")
    private java.lang.String targetTermGuid;
    @JsonProperty("target_term_name")
    private java.lang.String targetTermName;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("feature_guid")
    public java.lang.String getFeatureGuid() {
        return featureGuid;
    }

    @JsonProperty("feature_guid")
    public void setFeatureGuid(java.lang.String featureGuid) {
        this.featureGuid = featureGuid;
    }

    public WSFeatureTermPair withFeatureGuid(java.lang.String featureGuid) {
        this.featureGuid = featureGuid;
        return this;
    }

    @JsonProperty("feature_name")
    public java.lang.String getFeatureName() {
        return featureName;
    }

    @JsonProperty("feature_name")
    public void setFeatureName(java.lang.String featureName) {
        this.featureName = featureName;
    }

    public WSFeatureTermPair withFeatureName(java.lang.String featureName) {
        this.featureName = featureName;
        return this;
    }

    @JsonProperty("feature_function")
    public java.lang.String getFeatureFunction() {
        return featureFunction;
    }

    @JsonProperty("feature_function")
    public void setFeatureFunction(java.lang.String featureFunction) {
        this.featureFunction = featureFunction;
    }

    public WSFeatureTermPair withFeatureFunction(java.lang.String featureFunction) {
        this.featureFunction = featureFunction;
        return this;
    }

    @JsonProperty("feature_aliases")
    public List<String> getFeatureAliases() {
        return featureAliases;
    }

    @JsonProperty("feature_aliases")
    public void setFeatureAliases(List<String> featureAliases) {
        this.featureAliases = featureAliases;
    }

    public WSFeatureTermPair withFeatureAliases(List<String> featureAliases) {
        this.featureAliases = featureAliases;
        return this;
    }

    @JsonProperty("with_expression")
    public Long getWithExpression() {
        return withExpression;
    }

    @JsonProperty("with_expression")
    public void setWithExpression(Long withExpression) {
        this.withExpression = withExpression;
    }

    public WSFeatureTermPair withWithExpression(Long withExpression) {
        this.withExpression = withExpression;
        return this;
    }

    @JsonProperty("with_fitness")
    public Long getWithFitness() {
        return withFitness;
    }

    @JsonProperty("with_fitness")
    public void setWithFitness(Long withFitness) {
        this.withFitness = withFitness;
    }

    public WSFeatureTermPair withWithFitness(Long withFitness) {
        this.withFitness = withFitness;
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

    public WSFeatureTermPair withRefTermGuid(java.lang.String refTermGuid) {
        this.refTermGuid = refTermGuid;
        return this;
    }

    @JsonProperty("ref_term_name")
    public java.lang.String getRefTermName() {
        return refTermName;
    }

    @JsonProperty("ref_term_name")
    public void setRefTermName(java.lang.String refTermName) {
        this.refTermName = refTermName;
    }

    public WSFeatureTermPair withRefTermName(java.lang.String refTermName) {
        this.refTermName = refTermName;
        return this;
    }

    @JsonProperty("target_term_guid")
    public java.lang.String getTargetTermGuid() {
        return targetTermGuid;
    }

    @JsonProperty("target_term_guid")
    public void setTargetTermGuid(java.lang.String targetTermGuid) {
        this.targetTermGuid = targetTermGuid;
    }

    public WSFeatureTermPair withTargetTermGuid(java.lang.String targetTermGuid) {
        this.targetTermGuid = targetTermGuid;
        return this;
    }

    @JsonProperty("target_term_name")
    public java.lang.String getTargetTermName() {
        return targetTermName;
    }

    @JsonProperty("target_term_name")
    public void setTargetTermName(java.lang.String targetTermName) {
        this.targetTermName = targetTermName;
    }

    public WSFeatureTermPair withTargetTermName(java.lang.String targetTermName) {
        this.targetTermName = targetTermName;
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
        return ((((((((((((((((((((((("WSFeatureTermPair"+" [featureGuid=")+ featureGuid)+", featureName=")+ featureName)+", featureFunction=")+ featureFunction)+", featureAliases=")+ featureAliases)+", withExpression=")+ withExpression)+", withFitness=")+ withFitness)+", refTermGuid=")+ refTermGuid)+", refTermName=")+ refTermName)+", targetTermGuid=")+ targetTermGuid)+", targetTermName=")+ targetTermName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
