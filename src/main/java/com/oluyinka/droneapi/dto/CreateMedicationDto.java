package com.oluyinka.droneapi.dto;

import javax.validation.constraints.Pattern;

public class CreateMedicationDto {

    @Pattern(regexp = "^[a-zA-Z0-9.\\-_]+$", message = "Name can only contain alphanumeric characters, hyphen, underscore and/or spaces")
    private String name;

    private Double weight;

    @Pattern(regexp = "[A-Z]{4,}", message = "Code must be at least 4 characters long and contain only uppercase letters")
    private String code;

    @Pattern(regexp = "(https?://.*\\.(?:png|jpg|gif))", message = "Image URL is not valid")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
