package com.integration.recruitee.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "kind",
        "required",
        "position",
        "body",
        "translations",
        "open_question_options",
        "options"
})
@Data
public class OpenQuestion {
    @JsonProperty("id")
    public int id;
    @JsonProperty("kind")
    public String kind;
    @JsonProperty("required")
    public boolean required;
    @JsonProperty("position")
    public int position;
    @JsonProperty("body")
    public String body;
    @JsonProperty("translations")
    public Translations translations;
    @JsonProperty("open_question_options")
    public List<Object> open_question_options;
    @JsonProperty("options")
    public Options options;

}
