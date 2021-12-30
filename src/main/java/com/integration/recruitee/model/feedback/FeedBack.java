package com.integration.recruitee.model.feedback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "phone",
    "rating",
    "review"
})
@Data
public class FeedBack {


    @JsonProperty("phone")
    public String phone;

    @JsonProperty("rating")
    public String rating;

    @JsonProperty("review")
    public String review;
}
