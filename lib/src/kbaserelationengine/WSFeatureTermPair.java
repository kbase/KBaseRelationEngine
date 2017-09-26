
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
    private String featureGuid;
    @JsonProperty("feature_name")
    private String featureName;
    @JsonProperty("feature_function")
    private String featureFunction;
    @JsonProperty("feature_aliases")
    private String featureAliases;
    @JsonProperty("with_expression")
    private Long withExpression;
    @JsonProperty("with_fitness")
    private Long withFitness;
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

    @JsonProperty("feature_function")
    public String getFeatureFunction() {
        return featureFunction;
    }

    @JsonProperty("feature_function")
    public void setFeatureFunction(String featureFunction) {
        this.featureFunction = featureFunction;
    }

    public WSFeatureTermPair withFeatureFunction(String featureFunction) {
        this.featureFunction = featureFunction;
        return this;
    }

    @JsonProperty("feature_aliases")
    public String getFeatureAliases() {
        return featureAliases;
    }

    @JsonProperty("feature_aliases")
    public void setFeatureAliases(String featureAliases) {
        this.featureAliases = featureAliases;
    }

    public WSFeatureTermPair withFeatureAliases(String featureAliases) {
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
        return ((((((((((((((((((((((("WSFeatureTermPair"+" [featureGuid=")+ featureGuid)+", featureName=")+ featureName)+", featureFunction=")+ featureFunction)+", featureAliases=")+ featureAliases)+", withExpression=")+ withExpression)+", withFitness=")+ withFitness)+", refTermGuid=")+ refTermGuid)+", refTermName=")+ refTermName)+", targetTermGuid=")+ targetTermGuid)+", targetTermName=")+ targetTermName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
