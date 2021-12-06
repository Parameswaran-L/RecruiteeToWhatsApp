
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
    "candidate",
    "company",
    "details",
    "offer"
})
public class Payload {

    @JsonProperty("candidate")
    public Candidate candidate;
    @JsonProperty("company")
    public Company company;
    @JsonProperty("details")
    public Details details;
    @JsonProperty("offer")
    public Offer offer;
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
