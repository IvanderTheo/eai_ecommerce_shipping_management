package com.example.shipping.dto;

import jakarta.validation.constraints.*;

public class PackageRequest {

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    private Double weight;

    @NotNull(message = "Width is required")
    @DecimalMin(value = "0.1", message = "Width must be greater than 0")
    private Double width;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "0.1", message = "Height must be greater than 0")
    private Double height;

    @NotNull(message = "Depth is required")
    @DecimalMin(value = "0.1", message = "Depth must be greater than 0")
    private Double depth;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Min(value = 0, message = "Insurance value cannot be negative")
    private Double insuranceValue;

    public PackageRequest() {}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getWidth() { return width; }
    public void setWidth(Double width) { this.width = width; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Double getDepth() { return depth; }
    public void setDepth(Double depth) { this.depth = depth; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getInsuranceValue() { return insuranceValue; }
    public void setInsuranceValue(Double insuranceValue) { this.insuranceValue = insuranceValue; }
}
