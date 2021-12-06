
package com.integration.recruitee.model.pipeLineChange;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created_at",
    "department",
    "id",
    "kind",
    "slug",
    "tags",
    "title",
    "updated_at"
})
@Data
public class Offer {

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("department")
    public Department department;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("kind")
    public String kind;
    @JsonProperty("slug")
    public String slug;
    @JsonProperty("tags")
    public List<Tag> tags = null;
    @JsonProperty("title")
    public String title;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
