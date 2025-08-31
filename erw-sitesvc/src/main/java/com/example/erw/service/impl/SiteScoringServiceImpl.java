package com.example.erw.service.impl;

import com.example.erw.dto.SiteScoreRequest;
import com.example.erw.dto.SiteScoreResponse;
import com.example.erw.service.SiteScoringService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enhanced Rock Weathering (ERW) Site Scoring Service Implementation v3.0
 * 
 * Advanced metropolitan-scale ERW assessment incorporating 20 parameters based on 2025 industry
 * research from InPlanet (world's first verified credits), Lithos Carbon ($57M Frontier contracts),
 * UNDO Carbon, and Isometric Enhanced Weathering Protocol standards.
 * 
 * Enhanced scoring methodology (20 parameters):
 * - Environmental (35%): pH, temperature, rainfall, elevation, soil moisture, SOC, rainfall variability
 * - Logistics (30%): transport, infrastructure, basalt availability, monitoring capability
 * - Economic (25%): land scale, population density, energy/labor costs, carbon market access
 * - Advanced Factors (10%): regulatory stability, soil CEC, verification protocols
 * 
 * Key 2025 industry parameters:
 * - 3 tons basalt per hectare (InPlanet/Lithos standard)
 * - 333-700 kg CO2 removal per ton basalt (climate-dependent, tropical bonus)
 * - $57-180/ton CO2 price range (Frontier Climate verified contracts)
 * - Isometric verification protocol compliance
 * - Tropical climate 2x efficiency multiplier
 * 
 * @author ERW City Viability Analysis Platform
 * @version 3.0
 * @since 2025
 */
@Service
public class SiteScoringServiceImpl implements SiteScoringService {

    // Enhanced scoring constants based on 2025 research
    private static final double OPTIMAL_PH = 7.0; // Slightly acidic soils weather faster
    private static final double OPTIMAL_TEMPERATURE = 28.0; // °C - tropical optimum from research
    private static final double OPTIMAL_RAINFALL = 1500.0; // mm/year - increased for tropical efficiency
    private static final double MAX_ROAD_DISTANCE = 50.0; // km
    private static final double MAX_TRANSPORT_DISTANCE = 540.0; // km - research-backed limit
    private static final double TROPICAL_TEMPERATURE_THRESHOLD = 24.0; // °C - tropical bonus threshold
    private static final double OPTIMAL_SOIL_MOISTURE = 40.0; // % - optimal for weathering
    private static final double OPTIMAL_ELEVATION = 500.0; // m - moderate elevation optimum
    private static final double OPTIMAL_SOC = 3.0; // % - soil organic carbon optimum
    
    // Updated based on 2024 industry data
    private static final double TRUCK_EMISSIONS_KG_CO2_PER_KM_PER_TON = 0.27;
    private static final double BASALT_APPLICATION_TONS_PER_HECTARE = 3.0; // Lithos Carbon: 3 tons basalt for 1 ton CO2
    private static final double CO2_REMOVAL_BASE_KG_PER_TON_BASALT = 333.0; // Base rate
    private static final double CO2_REMOVAL_TROPICAL_KG_PER_TON_BASALT = 700.0; // Tropical climate bonus
    
    // Updated cost constants (USD) - 2024 industry data
    private static final double TRUCK_COST_PER_KM_PER_TON = 0.18; // Adjusted for 2024 fuel costs
    private static final double EQUIPMENT_COST_BASE = 16200.0; // per hectare per year - grinding/application equipment
    private static final double BASE_HECTARES = 100.0; // Default metropolitan pilot project size
    private static final double BASALT_COST_PER_TON = 35.0; // Raw basalt cost including grinding
    
    // Regional scaling factors for metropolitan analysis
    private static final double URBAN_LOGISTICS_PREMIUM = 1.15; // 15% premium for urban-area operations
    private static final double AGRICULTURAL_EFFICIENCY_FACTOR = 0.92; // Economies of scale in agricultural regions

    /**
     * Calculate comprehensive ERW site suitability score and analysis
     * 
     * Evaluates a potential ERW site across environmental, logistics, and economic factors
     * to produce an overall score (0-1) along with detailed cost analysis, environmental
     * impact assessment, and sustainability grading.
     * 
     * @param req Site parameters including location, environmental conditions, and local costs
     * @return Comprehensive analysis including score, cost breakdown, CO2 impact, and sustainability grade
     */
    @Override
    public SiteScoreResponse scoreSite(SiteScoreRequest req) {
        SiteScoreResponse response = new SiteScoreResponse();
        
        // Core Environmental Scoring (7 parameters)
        double phScore = calculatePhScore(req.getSoilPh());
        double temperatureScore = calculateTemperatureScore(req.getAvgTemperatureC());
        double rainfallScore = calculateRainfallScore(req.getRainfallMm());
        double elevationScore = calculateElevationScore(req.getElevationMeters());
        double soilMoistureScore = calculateSoilMoistureScore(req.getSoilMoisturePercent());
        double socScore = calculateSocScore(req.getSoilOrganicCarbonPercent());
        double rainfallVariabilityScore = calculateRainfallVariabilityScore(req.getAnnualRainfallVariability());
        
        // Core Logistics Scoring (4 parameters) 
        double roadAccessScore = calculateRoadAccessScore(req.getDistanceToRoadKm());
        double transportScore = calculateTransportScore(req.getBasaltTransportDistanceKm());
        double infrastructureScore = req.getInfrastructureQualityIndex();
        double basaltScore = req.getBasaltAvailabilityIndex();
        
        // Core Economic Scoring (5 parameters)
        double landScore = calculateLandScore(req.getAgriculturalLandHectares());
        double populationScore = calculatePopulationScore(req.getPopulationDensityPerKm2());
        double energyScore = calculateEnergyScore(req.getEnergyCostPerKWh());
        double laborScore = calculateLaborScore(req.getLaborCostPerHour());
        double carbonMarketScore = req.getCarbonMarketAccessibility();
        
        // Advanced Factors Scoring (4 parameters)
        double regulatoryScore = req.getRegulatoryStabilityIndex();
        double monitoringScore = req.getMonitoringCapabilityIndex();
        double soilCecScore = calculateSoilCecScore(req.getSoilCecMeqPer100g());
        
        // Calculate weighted category scores (20 parameters total)
        double environmentScore = (phScore * 0.2 + temperatureScore * 0.2 + rainfallScore * 0.15 + 
                                 elevationScore * 0.15 + soilMoistureScore * 0.15 + socScore * 0.1 + rainfallVariabilityScore * 0.05);
        double logisticsScore = (roadAccessScore * 0.25 + transportScore * 0.35 + infrastructureScore * 0.2 + 
                                basaltScore * 0.2);
        double economicScore = (landScore * 0.3 + populationScore * 0.15 + energyScore * 0.2 + 
                               laborScore * 0.2 + carbonMarketScore * 0.15);
        double advancedScore = (regulatoryScore * 0.3 + monitoringScore * 0.3 + soilCecScore * 0.2 + 
                               calculateClimateBonus(req.getAvgTemperatureC(), req.getRainfallMm()) * 0.2);
        
        // Overall score with updated weights (Environmental 35%, Logistics 30%, Economic 25%, Advanced 10%)
        double overallScore = environmentScore * 0.35 + logisticsScore * 0.30 + economicScore * 0.25 + advancedScore * 0.10;
        
        // Score breakdown
        Map<String, Double> breakdown = new LinkedHashMap<>();
        breakdown.put("environmental", round(environmentScore));
        breakdown.put("logistics", round(logisticsScore));
        breakdown.put("economic", round(economicScore));
        breakdown.put("soilPh", round(phScore));
        breakdown.put("temperature", round(temperatureScore));
        breakdown.put("rainfall", round(rainfallScore));
        breakdown.put("roadAccess", round(roadAccessScore));
        breakdown.put("transport", round(transportScore));
        breakdown.put("infrastructure", round(infrastructureScore));
        breakdown.put("basaltAvailability", round(basaltScore));
        
        response.setScore(round(overallScore));
        response.setBreakdown(breakdown);
        
        // Calculate project capacity and environmental impact
        double projectHectares = Math.min(req.getAgriculturalLandHectares(), BASE_HECTARES);
        response.setProjectCapacityHectares(projectHectares);
        
        // CO2 Calculations
        double basaltTonsPerYear = projectHectares * BASALT_APPLICATION_TONS_PER_HECTARE;
        double transportEmissions = calculateTransportEmissions(basaltTonsPerYear, req.getBasaltTransportDistanceKm());
        double co2RemovalRate = calculateCo2RemovalRate(req.getAvgTemperatureC(), req.getRainfallMm());
        double carbonRemoval = basaltTonsPerYear * co2RemovalRate * overallScore; // Climate-dependent efficiency
        double netCarbonImpact = carbonRemoval - transportEmissions;
        double carbonEfficiency = carbonRemoval > 0 ? netCarbonImpact / carbonRemoval : 0;
        
        response.setCo2EmissionsKgPerYear(round(transportEmissions));
        response.setCarbonRemovalKgPerYear(round(carbonRemoval));
        response.setNetCarbonImpactKgPerYear(round(netCarbonImpact));
        response.setCarbonEfficiencyRatio(round(carbonEfficiency));
        
        // Cost Calculations
        double basaltMaterialCost = basaltTonsPerYear * BASALT_COST_PER_TON;
        double transportCost = calculateTransportCost(basaltTonsPerYear, req.getBasaltTransportDistanceKm());
        double laborCost = calculateLaborCostTotal(projectHectares, req.getLaborCostPerHour());
        double equipmentCost = calculateEquipmentCost(projectHectares, req.getInfrastructureQualityIndex());
        double totalCost = basaltMaterialCost + transportCost + laborCost + equipmentCost;
        double costPerTonCO2 = carbonRemoval > 0 ? totalCost / (carbonRemoval / 1000.0) : 0; // Cost per ton CO2
        
        response.setBasaltMaterialCostUsdPerYear(round(basaltMaterialCost));
        response.setTransportCostUsdPerYear(round(transportCost));
        response.setLaborCostUsdPerYear(round(laborCost));
        response.setEquipmentCostUsdPerYear(round(equipmentCost));
        response.setTotalCostUsdPerYear(round(totalCost));
        response.setCostPerTonCo2Removed(round(costPerTonCO2));
        
        // Sustainability Grade
        response.setSustainabilityGrade(calculateSustainabilityGrade(overallScore, carbonEfficiency, costPerTonCO2));
        
        return response;
    }
    
    // Environmental scoring methods
    private double calculatePhScore(double pH) {
        if (OPTIMAL_PH <= 0) {
            throw new IllegalStateException("OPTIMAL_PH must be greater than 0");
        }
        return Math.max(0, 1.0 - Math.abs(pH - OPTIMAL_PH) / OPTIMAL_PH);
    }
    
    private double calculateTemperatureScore(double temp) {
        double diff = Math.abs(temp - OPTIMAL_TEMPERATURE);
        return Math.max(0, 1.0 - diff / 30.0); // Optimal range: -5°C to 55°C
    }
    
    private double calculateRainfallScore(double rainfall) {
        if (rainfall < 400) return rainfall / 400.0 * 0.3; // Very low rainfall
        if (rainfall < 800) return 0.3 + (rainfall - 400) / 400.0 * 0.4; // Low rainfall
        if (rainfall <= OPTIMAL_RAINFALL) return 0.7 + (rainfall - 800) / 400.0 * 0.3; // Good rainfall
        if (rainfall <= 2000) return 1.0 - (rainfall - OPTIMAL_RAINFALL) / 800.0 * 0.2; // High rainfall
        return Math.max(0.5, 1.0 - (rainfall - 2000) / 2000.0 * 0.5); // Very high rainfall
    }
    
    // Logistics scoring methods
    private double calculateRoadAccessScore(double distance) {
        return Math.max(0, 1.0 - distance / MAX_ROAD_DISTANCE);
    }
    
    private double calculateTransportScore(double distance) {
        // 2024 research shows 540km limiting distance where emissions offset capture
        // Below 100km is preferred for cost competitiveness
        if (distance <= 100) return 1.0; // Optimal range
        if (distance <= 300) return 0.8 - (distance - 100) / 200.0 * 0.3; // Good range
        if (distance <= MAX_TRANSPORT_DISTANCE) return 0.5 - (distance - 300) / 240.0 * 0.4; // Acceptable range
        return 0.1; // Beyond viable distance
    }
    
    // Economic scoring methods
    private double calculateLandScore(double hectares) {
        if (hectares < 10) return hectares / 10.0 * 0.3;
        if (hectares < 100) return 0.3 + (hectares - 10) / 90.0 * 0.5;
        if (hectares <= 1000) return 0.8 + (hectares - 100) / 900.0 * 0.2;
        return 1.0;
    }
    
    private double calculatePopulationScore(double density) {
        if (density < 50) return 1.0; // Low density - excellent
        if (density < 200) return 1.0 - (density - 50) / 150.0 * 0.3; // Medium density
        if (density < 500) return 0.7 - (density - 200) / 300.0 * 0.4; // High density
        return Math.max(0.1, 0.3 - (density - 500) / 1000.0 * 0.2); // Very high density
    }
    
    private double calculateEnergyScore(double cost) {
        // Lower energy costs are better for operations
        if (cost <= 0.05) return 1.0;
        if (cost <= 0.15) return 1.0 - (cost - 0.05) / 0.10 * 0.3;
        if (cost <= 0.30) return 0.7 - (cost - 0.15) / 0.15 * 0.4;
        return Math.max(0.1, 0.3 - (cost - 0.30) / 0.30 * 0.2);
    }
    
    private double calculateLaborScore(double cost) {
        // Lower labor costs are better for economics
        if (cost <= 5) return 1.0;
        if (cost <= 15) return 1.0 - (cost - 5) / 10.0 * 0.3;
        if (cost <= 30) return 0.7 - (cost - 15) / 15.0 * 0.4;
        return Math.max(0.1, 0.3 - (cost - 30) / 30.0 * 0.2);
    }
    
    // Environmental impact calculations
    private double calculateTransportEmissions(double basaltTons, double distanceKm) {
        return basaltTons * distanceKm * TRUCK_EMISSIONS_KG_CO2_PER_KM_PER_TON * 2; // Round trip
    }
    
    // Cost calculations
    private double calculateTransportCost(double basaltTons, double distanceKm) {
        double baseCost = basaltTons * distanceKm * TRUCK_COST_PER_KM_PER_TON * 2; // Round trip
        // Apply metropolitan logistics premium for distances under 100km (urban areas)
        if (distanceKm < 100) {
            baseCost *= URBAN_LOGISTICS_PREMIUM;
        } else {
            // Apply agricultural efficiency factor for rural operations
            baseCost *= AGRICULTURAL_EFFICIENCY_FACTOR;
        }
        return baseCost;
    }
    
    private double calculateLaborCostTotal(double hectares, double hourlyRate) {
        // Estimate: 2 workers per 10 hectares, 8 hours/day, 250 working days/year
        double workers = Math.max(1, hectares / 10.0 * 2);
        return workers * hourlyRate * 8 * 250;
    }
    
    private double calculateEquipmentCost(double hectares, double infrastructureIndex) {
        // Base equipment cost adjusted by infrastructure quality
        double baseCost = hectares * EQUIPMENT_COST_BASE;
        double infrastructureFactor = 2.0 - infrastructureIndex; // Better infrastructure = lower costs
        return baseCost * infrastructureFactor;
    }
    
    // Sustainability grading based on 2024 industry benchmarks
    private String calculateSustainabilityGrade(double score, double carbonEfficiency, double costPerTon) {
        // Updated based on 2024 research: competitive range $80-180/ton, target $100-150/ton
        if (score >= 0.8 && carbonEfficiency >= 0.9 && costPerTon <= 120) return "A+";
        if (score >= 0.7 && carbonEfficiency >= 0.8 && costPerTon <= 150) return "A";
        if (score >= 0.6 && carbonEfficiency >= 0.7 && costPerTon <= 180) return "B+";
        if (score >= 0.5 && carbonEfficiency >= 0.6 && costPerTon <= 220) return "B";
        if (score >= 0.4 && carbonEfficiency >= 0.5 && costPerTon <= 280) return "C+";
        if (score >= 0.3 && carbonEfficiency >= 0.4 && costPerTon <= 350) return "C";
        if (score >= 0.2 && carbonEfficiency >= 0.3 && costPerTon <= 500) return "D";
        return "F";
    }
    
    // Advanced scoring methods (2025 research-based)
    private double calculateElevationScore(double elevation) {
        // Optimal elevation around 500m, decreases at extremes
        double diff = Math.abs(elevation - OPTIMAL_ELEVATION);
        return Math.max(0.2, 1.0 - (diff / 2000.0)); // Penalize extreme elevations
    }
    
    private double calculateSoilMoistureScore(double moisturePercent) {
        // Optimal around 40%, critical for weathering reactions
        double diff = Math.abs(moisturePercent - OPTIMAL_SOIL_MOISTURE);
        return Math.max(0.1, 1.0 - (diff / 50.0));
    }
    
    private double calculateSocScore(double socPercent) {
        // Higher SOC improves soil health and weathering
        double normalized = socPercent / OPTIMAL_SOC;
        return Math.min(1.0, normalized);
    }
    
    private double calculateRainfallVariabilityScore(double variability) {
        // Lower variability is better for consistent weathering
        return Math.max(0.3, 1.0 - (variability / 2000.0));
    }
    
    private double calculateSoilCecScore(double cec) {
        // Higher CEC indicates better soil buffering capacity
        return Math.min(1.0, cec / 25.0); // 25 meq/100g as high CEC
    }
    
    private double calculateClimateBonus(double temperature, double rainfall) {
        // Tropical climate bonus based on 2025 research
        boolean isTropical = temperature >= TROPICAL_TEMPERATURE_THRESHOLD && rainfall >= 1200;
        return isTropical ? 1.0 : 0.6; // Significant bonus for tropical climates
    }
    
    // Enhanced CO2 calculation with climate dependency
    private double calculateCo2RemovalRate(double temperature, double rainfall) {
        boolean isTropical = temperature >= TROPICAL_TEMPERATURE_THRESHOLD && rainfall >= 1200;
        return isTropical ? CO2_REMOVAL_TROPICAL_KG_PER_TON_BASALT : CO2_REMOVAL_BASE_KG_PER_TON_BASALT;
    }
    
    // Utility methods (removed unused clamp method)
    
    private static double round(double v) { 
        return Math.round(v * 100.0) / 100.0; 
    }
}
