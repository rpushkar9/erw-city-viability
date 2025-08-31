package com.example.erw.dto;

import java.util.Map;

public class SiteScoreResponse {
    // Scoring
    private double score;
    private Map<String, Double> breakdown;
    
    // Environmental Impact
    private double co2EmissionsKgPerYear;
    private double carbonRemovalKgPerYear;
    private double netCarbonImpactKgPerYear;
    private double carbonEfficiencyRatio;
    
    // Cost Analysis
    private double basaltMaterialCostUsdPerYear;
    private double transportCostUsdPerYear;
    private double laborCostUsdPerYear;
    private double equipmentCostUsdPerYear;
    private double totalCostUsdPerYear;
    private double costPerTonCo2Removed;
    
    // Site Metrics
    private double projectCapacityHectares;
    private String sustainabilityGrade;

    public SiteScoreResponse() {}

    public SiteScoreResponse(double score, Map<String, Double> breakdown) {
        this.score = score;
        this.breakdown = breakdown;
    }

    // Getters and Setters
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public Map<String, Double> getBreakdown() { return breakdown; }
    public void setBreakdown(Map<String, Double> breakdown) { this.breakdown = breakdown; }
    
    public double getCo2EmissionsKgPerYear() { return co2EmissionsKgPerYear; }
    public void setCo2EmissionsKgPerYear(double co2EmissionsKgPerYear) { this.co2EmissionsKgPerYear = co2EmissionsKgPerYear; }
    public double getCarbonRemovalKgPerYear() { return carbonRemovalKgPerYear; }
    public void setCarbonRemovalKgPerYear(double carbonRemovalKgPerYear) { this.carbonRemovalKgPerYear = carbonRemovalKgPerYear; }
    public double getNetCarbonImpactKgPerYear() { return netCarbonImpactKgPerYear; }
    public void setNetCarbonImpactKgPerYear(double netCarbonImpactKgPerYear) { this.netCarbonImpactKgPerYear = netCarbonImpactKgPerYear; }
    public double getCarbonEfficiencyRatio() { return carbonEfficiencyRatio; }
    public void setCarbonEfficiencyRatio(double carbonEfficiencyRatio) { this.carbonEfficiencyRatio = carbonEfficiencyRatio; }
    
    public double getBasaltMaterialCostUsdPerYear() { return basaltMaterialCostUsdPerYear; }
    public void setBasaltMaterialCostUsdPerYear(double basaltMaterialCostUsdPerYear) { this.basaltMaterialCostUsdPerYear = basaltMaterialCostUsdPerYear; }
    public double getTransportCostUsdPerYear() { return transportCostUsdPerYear; }
    public void setTransportCostUsdPerYear(double transportCostUsdPerYear) { this.transportCostUsdPerYear = transportCostUsdPerYear; }
    public double getLaborCostUsdPerYear() { return laborCostUsdPerYear; }
    public void setLaborCostUsdPerYear(double laborCostUsdPerYear) { this.laborCostUsdPerYear = laborCostUsdPerYear; }
    public double getEquipmentCostUsdPerYear() { return equipmentCostUsdPerYear; }
    public void setEquipmentCostUsdPerYear(double equipmentCostUsdPerYear) { this.equipmentCostUsdPerYear = equipmentCostUsdPerYear; }
    public double getTotalCostUsdPerYear() { return totalCostUsdPerYear; }
    public void setTotalCostUsdPerYear(double totalCostUsdPerYear) { this.totalCostUsdPerYear = totalCostUsdPerYear; }
    public double getCostPerTonCo2Removed() { return costPerTonCo2Removed; }
    public void setCostPerTonCo2Removed(double costPerTonCo2Removed) { this.costPerTonCo2Removed = costPerTonCo2Removed; }
    
    public double getProjectCapacityHectares() { return projectCapacityHectares; }
    public void setProjectCapacityHectares(double projectCapacityHectares) { this.projectCapacityHectares = projectCapacityHectares; }
    public String getSustainabilityGrade() { return sustainabilityGrade; }
    public void setSustainabilityGrade(String sustainabilityGrade) { this.sustainabilityGrade = sustainabilityGrade; }
}
