
package com.integration.recruitee.model.apply;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.integration.recruitee.model.Candidate;
import com.integration.recruitee.model.Company;
import com.integration.recruitee.model.Details;
import com.integration.recruitee.model.Offer;
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
    "candidate",
    "company",
    "details",
    "offers"
})
public class Payload {

    @JsonProperty("candidate")
    public Candidate candidate;
    @JsonProperty("company")
    public Company company;
    @JsonProperty("details")
    public Details details;
    @JsonProperty("offers")
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
