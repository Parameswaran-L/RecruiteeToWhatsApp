
package com.integration.recruitee.model.pipeLineChange;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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
public class RecrutieeResponse {

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
