package com.example.shipping.dto;

import jakarta.validation.constraints.*;

public class CarrierRequest {

    @NotBlank(message = "Carrier name cannot be empty")
    @Size(min = 3, message = "Carrier name must be at least 3 characters")
    private String name;

    @NotBlank(message = "Carrier code cannot be empty")
    @Size(min = 2, max = 10, message = "Carrier code must be between 2 and 10 characters")
    private String code;

    @NotNull(message = "Cost per kg is required")
    @Min(value = 0, message = "Cost per kg must be greater than 0")
    private Double costPerKg;

    @NotNull(message = "Estimated days is required")
    @Min(value = 1, message = "Estimated days must be at least 1")
    private Long estimatedDays;

    private String description;

    public CarrierRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Double getCostPerKg() { return costPerKg; }
    public void setCostPerKg(Double costPerKg) { this.costPerKg = costPerKg; }

    public Long getEstimatedDays() { return estimatedDays; }
    public void setEstimatedDays(Long estimatedDays) { this.estimatedDays = estimatedDays; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
