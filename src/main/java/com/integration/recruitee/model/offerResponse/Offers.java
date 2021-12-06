package com.integration.recruitee.model.offerResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "slug",
        "position",
        "status",
        "options_phone",
        "options_photo",
        "options_cover_letter",
        "options_cv",
        "remote",
        "country_code",
        "state_code",
        "postal_code",
        "min_hours",
        "max_hours",
        "title",
        "description",
        "requirements",
        "location",
        "city",
        "country",
        "careers_url",
        "careers_apply_url",
        "company_name",
        "department",
        "created_at",
        "updated_at",
        "published_at",
        "close_at",
        "employment_type_code",
        "category_code",
        "experience_code",
        "education_code",
        "tags",
        "translations",
        "open_questions"
})
@Data
public class Offers {
    @JsonProperty("id")
    public int id;
    @JsonProperty("slug")
    public String slug;
    @JsonProperty("position")
    public int position;
    @JsonProperty("status")
    public String status;
    @JsonProperty("options_phone")
    public String options_phone;
    @JsonProperty("options_photo")
    public String options_photo;
    @JsonProperty("options_cover_letter")
    public String options_cover_letter;
    @JsonProperty("options_cv")
    public String options_cv;
    @JsonProperty("remote")
    public boolean remote;
    @JsonProperty("country_code")
    public String country_code;
    @JsonProperty("state_code")
    public String state_code;
    @JsonProperty("postal_code")
    public String postal_code;
    @JsonProperty("min_hours")
    public Object min_hours;
    @JsonProperty("max_hours")
    public Object max_hours;
    @JsonProperty("title")
    public String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("requirements")
    public String requirements;
    @JsonProperty("location")
    public String location;
    @JsonProperty("city")
    public String city;
    @JsonProperty("country")
    public String country;
    @JsonProperty("careers_url")
    public String careers_url;
    @JsonProperty("careers_apply_url")
    public String careers_apply_url;
    @JsonProperty("company_name")
    public String company_name;
    @JsonProperty("department")
    public String department;
    @JsonProperty("created_at")
    public String created_at;
    @JsonProperty("updated_at")
    public String updated_at;
    @JsonProperty("published_at")
    public String published_at;
    @JsonProperty("close_at")
    public Object close_at;
    @JsonProperty("employment_type_code")
    public String employment_type_code;
    @JsonProperty("category_code")
    public String category_code;
    @JsonProperty("experience_code")
    public String experience_code;
    @JsonProperty("education_code")
    public String education_code;
    @JsonProperty("tags")
    public List<String> tags;
    @JsonProperty("translations")
    public Translations translations;
    @JsonProperty("open_questions")
    public List<OpenQuestion> open_questions;
}
