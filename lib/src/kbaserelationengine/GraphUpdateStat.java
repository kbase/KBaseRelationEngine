
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
 * <p>Original spec-file type: GraphUpdateStat</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "nodes_created",
    "relationships_created",
    "properties_set"
})
public class GraphUpdateStat {

    @JsonProperty("nodes_created")
    private Long nodesCreated;
    @JsonProperty("relationships_created")
    private Long relationshipsCreated;
    @JsonProperty("properties_set")
    private Long propertiesSet;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("nodes_created")
    public Long getNodesCreated() {
        return nodesCreated;
    }

    @JsonProperty("nodes_created")
    public void setNodesCreated(Long nodesCreated) {
        this.nodesCreated = nodesCreated;
    }

    public GraphUpdateStat withNodesCreated(Long nodesCreated) {
        this.nodesCreated = nodesCreated;
        return this;
    }

    @JsonProperty("relationships_created")
    public Long getRelationshipsCreated() {
        return relationshipsCreated;
    }

    @JsonProperty("relationships_created")
    public void setRelationshipsCreated(Long relationshipsCreated) {
        this.relationshipsCreated = relationshipsCreated;
    }

    public GraphUpdateStat withRelationshipsCreated(Long relationshipsCreated) {
        this.relationshipsCreated = relationshipsCreated;
        return this;
    }

    @JsonProperty("properties_set")
    public Long getPropertiesSet() {
        return propertiesSet;
    }

    @JsonProperty("properties_set")
    public void setPropertiesSet(Long propertiesSet) {
        this.propertiesSet = propertiesSet;
    }

    public GraphUpdateStat withPropertiesSet(Long propertiesSet) {
        this.propertiesSet = propertiesSet;
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
        return ((((((((("GraphUpdateStat"+" [nodesCreated=")+ nodesCreated)+", relationshipsCreated=")+ relationshipsCreated)+", propertiesSet=")+ propertiesSet)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
