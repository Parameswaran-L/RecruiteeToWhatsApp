
package com.integration.recruitee.model.applyCandidate;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "attempt_count",
    "created_at",
    "event_subtype",
    "event_type",
    "id",
    "payload"
})
public class AppliedRecrutieeResponse {

    @JsonProperty("attempt_count")
    public Integer attemptCount;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("event_subtype")
    public String eventSubtype;
    @JsonProperty("event_type")
    public String eventType;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("payload")
    public Payload payload;
  
}
