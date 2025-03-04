
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
    "emails",
    "id",
    "name",
    "phones",
    "photo_thumb_url",
    "referrer",
    "source",
    "updated_at"
})
@Data
public class Candidate {

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("emails")
    public List<String> emails = null;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("phones")
    public List<Object> phones = null;
    @JsonProperty("photo_thumb_url")
    public String photoThumbUrl;
    @JsonProperty("referrer")
    public Object referrer;
    @JsonProperty("source")
    public String source;
    @JsonProperty("updated_at")
    public String updatedAt;
}
