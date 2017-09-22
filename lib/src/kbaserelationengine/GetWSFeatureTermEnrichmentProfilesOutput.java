
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
 * <p>Original spec-file type: GetWSFeatureTermEnrichmentProfilesOutput</p>
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
    "profiles"
})
public class GetWSFeatureTermEnrichmentProfilesOutput {

    @JsonProperty("feature_guid")
    private String featureGuid;
    @JsonProperty("feature_name")
    private String featureName;
    @JsonProperty("ref_term_guid")
    private String refTermGuid;
    @JsonProperty("ref_term_name")
    private String refTermName;
    @JsonProperty("profiles")
    private List<TermEnrichmentProfile> profiles;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("feature_guid")
    public String getFeatureGuid() {
        return featureGuid;
    }

    @JsonProperty("feature_guid")
    public void setFeatureGuid(String featureGuid) {
        this.featureGuid = featureGuid;
    }

    public GetWSFeatureTermEnrichmentProfilesOutput withFeatureGuid(String featureGuid) {
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

    public GetWSFeatureTermEnrichmentProfilesOutput withFeatureName(String featureName) {
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

    public GetWSFeatureTermEnrichmentProfilesOutput withRefTermGuid(String refTermGuid) {
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

    public GetWSFeatureTermEnrichmentProfilesOutput withRefTermName(String refTermName) {
        this.refTermName = refTermName;
        return this;
    }

    @JsonProperty("profiles")
    public List<TermEnrichmentProfile> getProfiles() {
        return profiles;
    }

    @JsonProperty("profiles")
    public void setProfiles(List<TermEnrichmentProfile> profiles) {
        this.profiles = profiles;
    }

    public GetWSFeatureTermEnrichmentProfilesOutput withProfiles(List<TermEnrichmentProfile> profiles) {
        this.profiles = profiles;
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
        return ((((((((((((("GetWSFeatureTermEnrichmentProfilesOutput"+" [featureGuid=")+ featureGuid)+", featureName=")+ featureName)+", refTermGuid=")+ refTermGuid)+", refTermName=")+ refTermName)+", profiles=")+ profiles)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
