
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
 * <p>Original spec-file type: StoreRichWSGenomeParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome_ref",
    "features",
    "ws2ref_feature_guids"
})
public class StoreRichWSGenomeParams {

    @JsonProperty("genome_ref")
    private java.lang.String genomeRef;
    @JsonProperty("features")
    private List<WSFeature> features;
    @JsonProperty("ws2ref_feature_guids")
    private Map<String, String> ws2refFeatureGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("genome_ref")
    public java.lang.String getGenomeRef() {
        return genomeRef;
    }

    @JsonProperty("genome_ref")
    public void setGenomeRef(java.lang.String genomeRef) {
        this.genomeRef = genomeRef;
    }

    public StoreRichWSGenomeParams withGenomeRef(java.lang.String genomeRef) {
        this.genomeRef = genomeRef;
        return this;
    }

    @JsonProperty("features")
    public List<WSFeature> getFeatures() {
        return features;
    }

    @JsonProperty("features")
    public void setFeatures(List<WSFeature> features) {
        this.features = features;
    }

    public StoreRichWSGenomeParams withFeatures(List<WSFeature> features) {
        this.features = features;
        return this;
    }

    @JsonProperty("ws2ref_feature_guids")
    public Map<String, String> getWs2refFeatureGuids() {
        return ws2refFeatureGuids;
    }

    @JsonProperty("ws2ref_feature_guids")
    public void setWs2refFeatureGuids(Map<String, String> ws2refFeatureGuids) {
        this.ws2refFeatureGuids = ws2refFeatureGuids;
    }

    public StoreRichWSGenomeParams withWs2refFeatureGuids(Map<String, String> ws2refFeatureGuids) {
        this.ws2refFeatureGuids = ws2refFeatureGuids;
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
        return ((((((((("StoreRichWSGenomeParams"+" [genomeRef=")+ genomeRef)+", features=")+ features)+", ws2refFeatureGuids=")+ ws2refFeatureGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
