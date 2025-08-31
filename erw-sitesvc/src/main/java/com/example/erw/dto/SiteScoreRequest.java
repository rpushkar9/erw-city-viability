package com.example.erw.dto;

import jakarta.validation.constraints.*;

public class SiteScoreRequest {
    // Basic Location
    @NotNull private Double latitude;
    @NotNull private Double longitude;
    
    // Environmental Parameters
    @NotNull @DecimalMin("0.0") private Double rainfallMm;
    @NotNull @DecimalMin("0.0") @DecimalMax("14.0") private Double soilPh;
    @NotNull @DecimalMin("-50.0") @DecimalMax("60.0") private Double avgTemperatureC;
    
    // Logistics Parameters
    @NotNull @DecimalMin("0.0") private Double distanceToRoadKm;
    @NotNull @DecimalMin("0.0") @DecimalMax("1000.0") private Double basaltTransportDistanceKm;
    @NotNull @DecimalMin("0.0") @DecimalMax("1.0") private Double basaltAvailabilityIndex;
    @NotNull @DecimalMin("0.0") @DecimalMax("1.0") private Double infrastructureQualityIndex;
    
    // Economic Parameters - Updated for metropolitan scale
    @NotNull @DecimalMin("0.0") @DecimalMax("20000000.0") private Double agriculturalLandHectares; // Up to 20M hectares for large regions
    @NotNull @DecimalMin("0.0") @DecimalMax("10000.0") private Double populationDensityPerKm2;
    @NotNull @DecimalMin("0.01") @DecimalMax("1.0") private Double energyCostPerKWh;
    @NotNull @DecimalMin("1.0") @DecimalMax("200.0") private Double laborCostPerHour; // Up to $200/hour for expensive regions
    
    // Advanced Parameters - Based on 2025 Research
    @NotNull @DecimalMin("0.0") @DecimalMax("5000.0") private Double annualRainfallVariability; // mm std deviation
    @NotNull @DecimalMin("0.0") @DecimalMax("100.0") private Double soilOrganicCarbonPercent; // SOC %
    @NotNull @DecimalMin("0.0") @DecimalMax("3000.0") private Double elevationMeters; // Elevation in meters
    @NotNull @DecimalMin("0.0") @DecimalMax("1.0") private Double regulatoryStabilityIndex; // Regulatory environment 0-1
    @NotNull @DecimalMin("10.0") @DecimalMax("90.0") private Double soilMoisturePercent; // Soil moisture %
    @NotNull @DecimalMin("0.0") @DecimalMax("1.0") private Double carbonMarketAccessibility; // Access to carbon markets 0-1
    @NotNull @DecimalMin("0.0") @DecimalMax("50.0") private Double soilCecMeqPer100g; // Cation exchange capacity
    @NotNull @DecimalMin("0.0") @DecimalMax("1.0") private Double monitoringCapabilityIndex; // MRV infrastructure 0-1

    // Getters and Setters
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public Double getRainfallMm() { return rainfallMm; }
    public void setRainfallMm(Double rainfallMm) { this.rainfallMm = rainfallMm; }
    public Double getSoilPh() { return soilPh; }
    public void setSoilPh(Double soilPh) { this.soilPh = soilPh; }
    public Double getAvgTemperatureC() { return avgTemperatureC; }
    public void setAvgTemperatureC(Double avgTemperatureC) { this.avgTemperatureC = avgTemperatureC; }
    
    public Double getDistanceToRoadKm() { return distanceToRoadKm; }
    public void setDistanceToRoadKm(Double distanceToRoadKm) { this.distanceToRoadKm = distanceToRoadKm; }
    public Double getBasaltTransportDistanceKm() { return basaltTransportDistanceKm; }
    public void setBasaltTransportDistanceKm(Double basaltTransportDistanceKm) { this.basaltTransportDistanceKm = basaltTransportDistanceKm; }
    public Double getBasaltAvailabilityIndex() { return basaltAvailabilityIndex; }
    public void setBasaltAvailabilityIndex(Double basaltAvailabilityIndex) { this.basaltAvailabilityIndex = basaltAvailabilityIndex; }
    public Double getInfrastructureQualityIndex() { return infrastructureQualityIndex; }
    public void setInfrastructureQualityIndex(Double infrastructureQualityIndex) { this.infrastructureQualityIndex = infrastructureQualityIndex; }
    
    public Double getAgriculturalLandHectares() { return agriculturalLandHectares; }
    public void setAgriculturalLandHectares(Double agriculturalLandHectares) { this.agriculturalLandHectares = agriculturalLandHectares; }
    public Double getPopulationDensityPerKm2() { return populationDensityPerKm2; }
    public void setPopulationDensityPerKm2(Double populationDensityPerKm2) { this.populationDensityPerKm2 = populationDensityPerKm2; }
    public Double getEnergyCostPerKWh() { return energyCostPerKWh; }
    public void setEnergyCostPerKWh(Double energyCostPerKWh) { this.energyCostPerKWh = energyCostPerKWh; }
    public Double getLaborCostPerHour() { return laborCostPerHour; }
    public void setLaborCostPerHour(Double laborCostPerHour) { this.laborCostPerHour = laborCostPerHour; }
    
    // Advanced Parameters Getters and Setters
    public Double getAnnualRainfallVariability() { return annualRainfallVariability; }
    public void setAnnualRainfallVariability(Double annualRainfallVariability) { this.annualRainfallVariability = annualRainfallVariability; }
    public Double getSoilOrganicCarbonPercent() { return soilOrganicCarbonPercent; }
    public void setSoilOrganicCarbonPercent(Double soilOrganicCarbonPercent) { this.soilOrganicCarbonPercent = soilOrganicCarbonPercent; }
    public Double getElevationMeters() { return elevationMeters; }
    public void setElevationMeters(Double elevationMeters) { this.elevationMeters = elevationMeters; }
    public Double getRegulatoryStabilityIndex() { return regulatoryStabilityIndex; }
    public void setRegulatoryStabilityIndex(Double regulatoryStabilityIndex) { this.regulatoryStabilityIndex = regulatoryStabilityIndex; }
    public Double getSoilMoisturePercent() { return soilMoisturePercent; }
    public void setSoilMoisturePercent(Double soilMoisturePercent) { this.soilMoisturePercent = soilMoisturePercent; }
    public Double getCarbonMarketAccessibility() { return carbonMarketAccessibility; }
    public void setCarbonMarketAccessibility(Double carbonMarketAccessibility) { this.carbonMarketAccessibility = carbonMarketAccessibility; }
    public Double getSoilCecMeqPer100g() { return soilCecMeqPer100g; }
    public void setSoilCecMeqPer100g(Double soilCecMeqPer100g) { this.soilCecMeqPer100g = soilCecMeqPer100g; }
    public Double getMonitoringCapabilityIndex() { return monitoringCapabilityIndex; }
    public void setMonitoringCapabilityIndex(Double monitoringCapabilityIndex) { this.monitoringCapabilityIndex = monitoringCapabilityIndex; }
}
