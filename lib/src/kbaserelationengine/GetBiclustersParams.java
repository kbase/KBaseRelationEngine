
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
 * <p>Original spec-file type: GetBiclustersParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "bicluster_guids"
})
public class GetBiclustersParams {

    @JsonProperty("bicluster_guids")
    private List<String> biclusterGuids;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("bicluster_guids")
    public List<String> getBiclusterGuids() {
        return biclusterGuids;
    }

    @JsonProperty("bicluster_guids")
    public void setBiclusterGuids(List<String> biclusterGuids) {
        this.biclusterGuids = biclusterGuids;
    }

    public GetBiclustersParams withBiclusterGuids(List<String> biclusterGuids) {
        this.biclusterGuids = biclusterGuids;
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
        return ((((("GetBiclustersParams"+" [biclusterGuids=")+ biclusterGuids)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
