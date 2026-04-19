package com.example.shipping.dto;

public class CarrierResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Double costPerKg;
    private Long estimatedDays;
    private Boolean active;

    public CarrierResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getCostPerKg() { return costPerKg; }
    public void setCostPerKg(Double costPerKg) { this.costPerKg = costPerKg; }

    public Long getEstimatedDays() { return estimatedDays; }
    public void setEstimatedDays(Long estimatedDays) { this.estimatedDays = estimatedDays; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
