
package com.integration.recruitee.model.applyCandidate;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
    "offers"
})
public class Payload {

    @JsonProperty("candidate")
    public Candidate candidate;
    @JsonProperty("company")
    public Company company;
    @JsonProperty("offers")
    public Offers offers;

}
