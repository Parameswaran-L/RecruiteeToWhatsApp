
package com.integration.recruitee.model.applyCandidate;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

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
public class Offers {

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

}
