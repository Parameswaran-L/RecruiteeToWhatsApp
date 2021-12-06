package com.integration.recruitee.model.offerResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "offers"
})
@Data
public class OfferResponse {

    @JsonProperty("offers")
    public List<Offers> offers;


}
