/**
 * Enhanced Rock Weathering Site Scoring Service - Frontend Application
 * 
 * Interactive web interface for ERW site assessment with comprehensive analysis
 * capabilities including real-time scoring, cost analysis, and environmental impact.
 * 
 * Features:
 * - Interactive Leaflet map with site markers and analysis panels
 * - Real-time site scoring with 12-parameter input forms
 * - Detailed cost breakdown and environmental impact analysis
 * - Industry-standard sustainability grading based on 2024 benchmarks
 * - Regional parameter estimation for realistic site assessments
 * 
 * Technologies:
 * - Leaflet.js for interactive mapping
 * - Chart.js for data visualization
 * - Vanilla JavaScript ES6+ for all interactions
 * - CSS Grid and Flexbox for responsive design
 * 
 * @version 2.0
 * @author ERW Site Assessment Platform
 * @since 2024
 */
class ERWApp {
    constructor() {
        this.sites = [];
        this.map = null;
        this.chart = null;
        this.init();
    }

    async init() {
        this.setupNavigation();
        this.setupForm();
        await this.loadSites();
        this.setupFilters();
        this.initializeMap();
        this.createChart();
        this.updateDashboard();
    }

    // Navigation Setup
    setupNavigation() {
        const navButtons = document.querySelectorAll('.nav-btn');
        const tabContents = document.querySelectorAll('.tab-content');

        navButtons.forEach(btn => {
            btn.addEventListener('click', () => {
                const targetTab = btn.dataset.tab;
                
                // Update active nav button
                navButtons.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
                
                // Update active tab content
                tabContents.forEach(tab => {
                    tab.classList.remove('active');
                    if (tab.id === targetTab) {
                        tab.classList.add('active');
                        // Initialize map when map tab is shown
                        if (targetTab === 'map' && this.map) {
                            setTimeout(() => this.map.invalidateSize(), 100);
                        }
                    }
                });
            });
        });
    }

    // Load Sites Data
    async loadSites() {
        try {
            const response = await fetch('/api/sites?size=100');
            const data = await response.json();
            this.sites = data.items || [];
            this.renderSites(this.sites);
            this.updateRegionFilter();
            return this.sites;
        } catch (error) {
            console.error('Error loading sites:', error);
            document.getElementById('sitesList').innerHTML = 
                '<div class="error">Error loading sites. Please refresh the page.</div>';
            return [];
        }
    }

    // Render Sites List
    renderSites(sites) {
        const container = document.getElementById('sitesList');
        
        if (!sites || sites.length === 0) {
            container.innerHTML = '<div class="no-results">No sites found matching your criteria.</div>';
            return;
        }

        container.innerHTML = sites.map(site => `
            <div class="site-item" onclick="app.showSiteAnalysis('${site.name}', ${site.latitude}, ${site.longitude}, '${site.region}', ${site.score})">
                <div class="site-header">
                    <div>
                        <div class="site-name">${site.name}</div>
                        <div class="site-details">
                            <div><i class="fas fa-map-marker-alt"></i> ${site.region}</div>
                            <div><i class="fas fa-crosshairs"></i> ${site.latitude.toFixed(3)}, ${site.longitude.toFixed(3)}</div>
                        </div>
                    </div>
                    <div class="site-score ${this.getScoreClass(site.score)}">${site.score.toFixed(2)}</div>
                </div>
            </div>
        `).join('');
    }

    // Get score class for styling
    getScoreClass(score) {
        if (score >= 0.7) return 'high';
        if (score >= 0.4) return 'medium';
        return 'low';
    }

    // Focus on site in map
    focusOnSite(lat, lng) {
        if (this.map) {
            // Switch to map tab
            document.querySelector('[data-tab="map"]').click();
            // Focus on the site
            setTimeout(() => {
                this.map.setView([lat, lng], 10);
            }, 200);
        }
    }

    // Show site analysis with scoring
    async showSiteAnalysis(siteName, lat, lng, region, score) {
        // Switch to map tab and focus on site
        this.focusOnSite(lat, lng);
        
        // Show and populate the analysis panel
        const panel = document.getElementById('siteAnalysisPanel');
        const panelContent = panel.querySelector('.panel-content');
        const panelName = document.getElementById('panelSiteName');
        const mapContent = document.querySelector('.map-content');
        
        // Update panel title
        panelName.textContent = siteName;
        
        // Show panel with animation
        panel.classList.remove('hidden');
        mapContent.classList.add('with-panel');
        
        // Show loading state
        panelContent.innerHTML = `
            <div class="loading-panel">
                <i class="fas fa-spinner fa-spin"></i>
                <p>Loading site analysis...</p>
            </div>
        `;
        
        // Calculate detailed analysis for this site
        await this.calculateSiteAnalysis(siteName, lat, lng, region, score);
    }

    // Calculate detailed site analysis
    async calculateSiteAnalysis(siteName, lat, lng, region, score) {
        try {
            // Use example parameters based on the region and existing site data
            const siteParams = this.generateSiteParameters(lat, lng, region);
            
            const response = await fetch('/api/sitescore', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(siteParams)
            });

            if (!response.ok) {
                throw new Error('Failed to calculate site analysis');
            }

            const result = await response.json();
            this.displaySiteAnalysis(siteName, lat, lng, region, result);
        } catch (error) {
            console.error('Error calculating site analysis:', error);
            this.displayAnalysisError(error.message);
        }
    }

    // Generate realistic site parameters based on location
    generateSiteParameters(lat, lng, region) {
        // Base parameters - will be refined based on research
        const params = {
            latitude: lat,
            longitude: lng,
            rainfallMm: 1000,
            soilPh: 7.0,
            avgTemperatureC: 25.0,
            distanceToRoadKm: 5.0,
            basaltTransportDistanceKm: 100,
            basaltAvailabilityIndex: 0.7,
            infrastructureQualityIndex: 0.6,
            agriculturalLandHectares: 500,
            populationDensityPerKm2: 150,
            energyCostPerKWh: 0.10,
            laborCostPerHour: 8.0
        };

        // Adjust parameters based on region
        if (region.includes('Madagascar')) {
            params.rainfallMm = 1400;
            params.avgTemperatureC = 22.0;
            params.laborCostPerHour = 3.5;
            params.energyCostPerKWh = 0.25;
            params.populationDensityPerKm2 = 80;
        } else if (region.includes('Karnataka') || region.includes('India')) {
            params.rainfallMm = 970;
            params.avgTemperatureC = 24.5;
            params.laborCostPerHour = 4.5;
            params.energyCostPerKWh = 0.08;
            params.populationDensityPerKm2 = 120;
            params.basaltTransportDistanceKm = 85;
        } else if (region.includes('Brazil')) {
            params.rainfallMm = 1800;
            params.avgTemperatureC = 26.0;
            params.laborCostPerHour = 6.0;
            params.energyCostPerKWh = 0.12;
            params.populationDensityPerKm2 = 200;
        } else if (region.includes('Australia')) {
            params.rainfallMm = 800;
            params.avgTemperatureC = 20.0;
            params.laborCostPerHour = 25.0;
            params.energyCostPerKWh = 0.22;
            params.populationDensityPerKm2 = 50;
            params.basaltTransportDistanceKm = 200;
        }

        return params;
    }

    // Display detailed site analysis
    displaySiteAnalysis(siteName, lat, lng, region, result) {
        const panelContent = document.querySelector('#siteAnalysisPanel .panel-content');
        
        panelContent.innerHTML = `
            <div class="site-overview">
                <h4><i class="fas fa-map-marker-alt"></i> Site Overview</h4>
                <div class="overview-grid">
                    <div class="overview-item">
                        <span class="label">Region:</span>
                        <span class="value">${region}</span>
                    </div>
                    <div class="overview-item">
                        <span class="label">Coordinates:</span>
                        <span class="value">${lat.toFixed(3)}, ${lng.toFixed(3)}</span>
                    </div>
                    <div class="overview-item">
                        <span class="label">Project Size:</span>
                        <span class="value">${result.projectCapacityHectares} hectares</span>
                    </div>
                    <div class="overview-item">
                        <span class="label">Sustainability:</span>
                        <span class="value grade-${result.sustainabilityGrade?.toLowerCase()}">${result.sustainabilityGrade}</span>
                    </div>
                </div>
            </div>

            <div class="score-section">
                <h4><i class="fas fa-chart-line"></i> Scoring Analysis</h4>
                <div class="main-score">
                    <div class="score-circle">
                        <span class="score-value">${result.score.toFixed(2)}</span>
                        <span class="score-label">Overall Score</span>
                    </div>
                </div>
                <div class="score-breakdown">
                    <div class="score-item">
                        <span class="score-name">Environmental</span>
                        <div class="score-bar">
                            <div class="score-fill" style="width: ${(result.breakdown.environmental * 100).toFixed(0)}%"></div>
                        </div>
                        <span class="score-val">${result.breakdown.environmental.toFixed(2)}</span>
                    </div>
                    <div class="score-item">
                        <span class="score-name">Logistics</span>
                        <div class="score-bar">
                            <div class="score-fill" style="width: ${(result.breakdown.logistics * 100).toFixed(0)}%"></div>
                        </div>
                        <span class="score-val">${result.breakdown.logistics.toFixed(2)}</span>
                    </div>
                    <div class="score-item">
                        <span class="score-name">Economic</span>
                        <div class="score-bar">
                            <div class="score-fill" style="width: ${(result.breakdown.economic * 100).toFixed(0)}%"></div>
                        </div>
                        <span class="score-val">${result.breakdown.economic.toFixed(2)}</span>
                    </div>
                </div>
            </div>

            <div class="environmental-section">
                <h4><i class="fas fa-leaf"></i> Environmental Impact</h4>
                <div class="impact-grid">
                    <div class="impact-item positive">
                        <i class="fas fa-arrow-down"></i>
                        <div>
                            <span class="impact-value">${(result.carbonRemovalKgPerYear / 1000).toFixed(1)}t</span>
                            <span class="impact-label">CO₂ Removal/Year</span>
                        </div>
                    </div>
                    <div class="impact-item negative">
                        <i class="fas fa-truck"></i>
                        <div>
                            <span class="impact-value">${(result.co2EmissionsKgPerYear / 1000).toFixed(1)}t</span>
                            <span class="impact-label">Transport Emissions</span>
                        </div>
                    </div>
                    <div class="impact-item ${result.netCarbonImpactKgPerYear > 0 ? 'positive' : 'negative'}">
                        <i class="fas fa-balance-scale"></i>
                        <div>
                            <span class="impact-value">${(result.netCarbonImpactKgPerYear / 1000).toFixed(1)}t</span>
                            <span class="impact-label">Net Impact/Year</span>
                        </div>
                    </div>
                    <div class="impact-item">
                        <i class="fas fa-percentage"></i>
                        <div>
                            <span class="impact-value">${(result.carbonEfficiencyRatio * 100).toFixed(0)}%</span>
                            <span class="impact-label">Carbon Efficiency</span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="economic-section">
                <h4><i class="fas fa-dollar-sign"></i> Economic Analysis</h4>
                <div class="cost-grid">
                    <div class="cost-item">
                        <span class="cost-name">Basalt Material</span>
                        <span class="cost-value">$${(result.basaltMaterialCostUsdPerYear / 1000).toFixed(0)}k/year</span>
                    </div>
                    <div class="cost-item">
                        <span class="cost-name">Transport</span>
                        <span class="cost-value">$${(result.transportCostUsdPerYear / 1000).toFixed(0)}k/year</span>
                    </div>
                    <div class="cost-item">
                        <span class="cost-name">Labor</span>
                        <span class="cost-value">$${(result.laborCostUsdPerYear / 1000).toFixed(0)}k/year</span>
                    </div>
                    <div class="cost-item">
                        <span class="cost-name">Equipment</span>
                        <span class="cost-value">$${(result.equipmentCostUsdPerYear / 1000).toFixed(0)}k/year</span>
                    </div>
                    <div class="cost-item total">
                        <span class="cost-name">Total Cost</span>
                        <span class="cost-value">$${(result.totalCostUsdPerYear / 1000000).toFixed(1)}M/year</span>
                    </div>
                    <div class="cost-item highlight">
                        <span class="cost-name">Cost per ton CO₂</span>
                        <span class="cost-value">$${result.costPerTonCo2Removed.toFixed(0)}</span>
                    </div>
                </div>
            </div>
        `;
    }

    // Display error in analysis panel
    displayAnalysisError(message) {
        const panelContent = document.querySelector('#siteAnalysisPanel .panel-content');
        panelContent.innerHTML = `
            <div class="error-panel">
                <i class="fas fa-exclamation-triangle"></i>
                <p>Error loading site analysis: ${message}</p>
                <button onclick="app.closeSitePanel()" class="btn-secondary">
                    <i class="fas fa-times"></i> Close
                </button>
            </div>
        `;
    }

    // Close site analysis panel
    closeSitePanel() {
        const panel = document.getElementById('siteAnalysisPanel');
        const mapContent = document.querySelector('.map-content');
        
        panel.classList.add('hidden');
        mapContent.classList.remove('with-panel');
        
        // Resize map after panel closes
        setTimeout(() => {
            if (this.map) {
                this.map.invalidateSize();
            }
        }, 300);
    }

    // Setup Filters
    setupFilters() {
        const regionFilter = document.getElementById('regionFilter');
        const sortBy = document.getElementById('sortBy');
        const sortDir = document.getElementById('sortDir');

        [regionFilter, sortBy, sortDir].forEach(filter => {
            filter.addEventListener('change', () => this.applyFilters());
        });
    }

    // Update Region Filter Options
    updateRegionFilter() {
        const regionFilter = document.getElementById('regionFilter');
        const regions = [...new Set(this.sites.map(site => site.region))].sort();
        
        regionFilter.innerHTML = '<option value="">All Regions</option>' +
            regions.map(region => `<option value="${region}">${region}</option>`).join('');
    }

    // Apply Filters
    async applyFilters() {
        const region = document.getElementById('regionFilter').value;
        const sort = document.getElementById('sortBy').value;
        const dir = document.getElementById('sortDir').value;

        const params = new URLSearchParams();
        if (region) params.append('region', region);
        params.append('sort', sort);
        params.append('dir', dir);
        params.append('size', '100');

        try {
            const response = await fetch(`/api/sites?${params}`);
            const data = await response.json();
            this.renderSites(data.items);
        } catch (error) {
            console.error('Error applying filters:', error);
        }
    }

    // Initialize Map
    initializeMap() {
        // Initialize Leaflet map centered on Madagascar
        this.map = L.map('mapContainer').setView([-18.8, 47.0], 6);

        // Add OpenStreetMap tiles
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors'
        }).addTo(this.map);

        // Add site markers
        this.addSiteMarkers();
    }

    // Add Site Markers to Map
    addSiteMarkers() {
        if (!this.map || !this.sites.length) return;

        this.sites.forEach(site => {
            const color = this.getMarkerColor(site.score);
            const marker = L.circleMarker([site.latitude, site.longitude], {
                radius: 10,
                fillColor: color,
                color: '#fff',
                weight: 2,
                opacity: 1,
                fillOpacity: 0.8
            }).addTo(this.map);

            // Add popup with site information and analysis button
            marker.bindPopup(`
                <div class="map-popup">
                    <h3>${site.name}</h3>
                    <p><strong>Region:</strong> ${site.region}</p>
                    <p><strong>Coordinates:</strong> ${site.latitude.toFixed(3)}, ${site.longitude.toFixed(3)}</p>
                    <p><strong>Score:</strong> <span class="score ${this.getScoreClass(site.score)}">${site.score.toFixed(2)}</span></p>
                    <button onclick="app.showSiteAnalysis('${site.name}', ${site.latitude}, ${site.longitude}, '${site.region}', ${site.score})" 
                            class="btn-primary btn-small">
                        <i class="fas fa-chart-line"></i> View Analysis
                    </button>
                </div>
            `);

            // Also add click event to show analysis directly
            marker.on('click', () => {
                setTimeout(() => {
                    this.showSiteAnalysis(site.name, site.latitude, site.longitude, site.region, site.score);
                }, 100);
            });
        });
    }

    // Get marker color based on score
    getMarkerColor(score) {
        if (score >= 0.7) return '#4CAF50';  // High - Green
        if (score >= 0.4) return '#FF9800';  // Medium - Orange
        return '#f44336';                    // Low - Red
    }

    // Create Score Distribution Chart
    createChart() {
        const ctx = document.getElementById('scoreChart');
        if (!ctx) return;

        const scoreRanges = {
            'High (0.7-1.0)': 0,
            'Medium (0.4-0.69)': 0,
            'Low (0.0-0.39)': 0
        };

        this.sites.forEach(site => {
            if (site.score >= 0.7) scoreRanges['High (0.7-1.0)']++;
            else if (site.score >= 0.4) scoreRanges['Medium (0.4-0.69)']++;
            else scoreRanges['Low (0.0-0.39)']++;
        });

        this.chart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: Object.keys(scoreRanges),
                datasets: [{
                    data: Object.values(scoreRanges),
                    backgroundColor: ['#4CAF50', '#FF9800', '#f44336'],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    }

    // Update Dashboard Stats
    updateDashboard() {
        const totalSites = this.sites.length;
        const avgScore = totalSites > 0 
            ? (this.sites.reduce((sum, site) => sum + site.score, 0) / totalSites)
            : 0;
        const totalRegions = new Set(this.sites.map(site => site.region)).size;

        document.getElementById('totalSites').textContent = totalSites;
        document.getElementById('avgScore').textContent = avgScore.toFixed(2);
        document.getElementById('totalRegions').textContent = totalRegions;
    }

    // Setup Score Calculation Form
    setupForm() {
        const form = document.getElementById('scoringForm');
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            await this.calculateScore();
        });

        // Add example data button
        const exampleBtn = document.createElement('button');
        exampleBtn.type = 'button';
        exampleBtn.className = 'btn-secondary';
        exampleBtn.innerHTML = '<i class="fas fa-lightbulb"></i> Load Example Data';
        exampleBtn.onclick = () => this.loadExampleData();
        
        form.appendChild(exampleBtn);
    }

    // Load Example Data into Form (South Indian location)
    loadExampleData() {
        // Example: Bangalore Rural site with comprehensive parameters
        document.getElementById('latitude').value = 12.970;
        document.getElementById('longitude').value = 77.590;
        
        // Environmental
        document.getElementById('rainfallMm').value = 970;
        document.getElementById('soilPh').value = 6.8;
        document.getElementById('avgTemperatureC').value = 24.5;
        
        // Logistics
        document.getElementById('distanceToRoadKm').value = 3.5;
        document.getElementById('basaltTransportDistanceKm').value = 85;
        document.getElementById('basaltAvailabilityIndex').value = 0.85;
        document.getElementById('infrastructureQualityIndex').value = 0.78;
        
        // Economic
        document.getElementById('agriculturalLandHectares').value = 500;
        document.getElementById('populationDensityPerKm2').value = 120;
        document.getElementById('energyCostPerKWh').value = 0.08;
        document.getElementById('laborCostPerHour').value = 4.5;
    }

    // Calculate Site Score
    async calculateScore() {
        const formData = {
            // Location
            latitude: parseFloat(document.getElementById('latitude').value),
            longitude: parseFloat(document.getElementById('longitude').value),
            
            // Environmental
            rainfallMm: parseFloat(document.getElementById('rainfallMm').value),
            soilPh: parseFloat(document.getElementById('soilPh').value),
            avgTemperatureC: parseFloat(document.getElementById('avgTemperatureC').value),
            
            // Logistics
            distanceToRoadKm: parseFloat(document.getElementById('distanceToRoadKm').value),
            basaltTransportDistanceKm: parseFloat(document.getElementById('basaltTransportDistanceKm').value),
            basaltAvailabilityIndex: parseFloat(document.getElementById('basaltAvailabilityIndex').value),
            infrastructureQualityIndex: parseFloat(document.getElementById('infrastructureQualityIndex').value),
            
            // Economic
            agriculturalLandHectares: parseFloat(document.getElementById('agriculturalLandHectares').value),
            populationDensityPerKm2: parseFloat(document.getElementById('populationDensityPerKm2').value),
            energyCostPerKWh: parseFloat(document.getElementById('energyCostPerKWh').value),
            laborCostPerHour: parseFloat(document.getElementById('laborCostPerHour').value)
        };

        try {
            const response = await fetch('/api/sitescore', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Calculation failed');
            }

            const result = await response.json();
            this.displayScoreResult(result);
        } catch (error) {
            console.error('Error calculating score:', error);
            this.displayError(error.message);
        }
    }

    // Display Score Result
    displayScoreResult(result) {
        const container = document.getElementById('scoreResult');
        
        container.innerHTML = `
            <div class="score-header">
                <div class="score-display">
                    <div class="score-value">${result.score.toFixed(2)}</div>
                    <div class="score-label">Overall Site Score</div>
                </div>
                <div class="sustainability-grade">
                    <div class="grade-value ${this.getGradeClass(result.sustainabilityGrade)}">${result.sustainabilityGrade || 'N/A'}</div>
                    <div class="grade-label">Sustainability Grade</div>
                </div>
            </div>

            <div class="metrics-tabs">
                <button class="tab-btn active" onclick="showMetricsTab(event, 'scoring')">
                    <i class="fas fa-chart-line"></i> Scoring Breakdown
                </button>
                <button class="tab-btn" onclick="showMetricsTab(event, 'environmental')">
                    <i class="fas fa-leaf"></i> Environmental Impact
                </button>
                <button class="tab-btn" onclick="showMetricsTab(event, 'economics')">
                    <i class="fas fa-dollar-sign"></i> Cost Analysis
                </button>
            </div>

            <div id="scoring" class="metrics-content active">
                <h4>Score Components</h4>
                <div class="breakdown-grid">
                    <div class="breakdown-item major">
                        <div class="breakdown-value">${result.breakdown.environmental?.toFixed(2) || '0.00'}</div>
                        <div class="breakdown-label">Environmental (40%)</div>
                    </div>
                    <div class="breakdown-item major">
                        <div class="breakdown-value">${result.breakdown.logistics?.toFixed(2) || '0.00'}</div>
                        <div class="breakdown-label">Logistics (35%)</div>
                    </div>
                    <div class="breakdown-item major">
                        <div class="breakdown-value">${result.breakdown.economic?.toFixed(2) || '0.00'}</div>
                        <div class="breakdown-label">Economic (25%)</div>
                    </div>
                </div>
                
                <h4>Detailed Factors</h4>
                <div class="breakdown-grid small">
                    ${Object.entries(result.breakdown)
                        .filter(([key]) => !['environmental', 'logistics', 'economic'].includes(key))
                        .map(([key, value]) => `
                        <div class="breakdown-item">
                            <div class="breakdown-value">${value.toFixed(2)}</div>
                            <div class="breakdown-label">${this.formatBreakdownLabel(key)}</div>
                        </div>
                    `).join('')}
                </div>
            </div>

            <div id="environmental" class="metrics-content">
                <h4>Carbon Impact Analysis</h4>
                <div class="impact-summary">
                    <div class="impact-item positive">
                        <i class="fas fa-arrow-down"></i>
                        <span class="impact-value">${(result.carbonRemovalKgPerYear / 1000).toFixed(1)} tons</span>
                        <span class="impact-label">CO₂ Removal/Year</span>
                    </div>
                    <div class="impact-item negative">
                        <i class="fas fa-arrow-up"></i>
                        <span class="impact-value">${(result.co2EmissionsKgPerYear / 1000).toFixed(1)} tons</span>
                        <span class="impact-label">Transport Emissions/Year</span>
                    </div>
                    <div class="impact-item net ${result.netCarbonImpactKgPerYear > 0 ? 'positive' : 'negative'}">
                        <i class="fas fa-balance-scale"></i>
                        <span class="impact-value">${(result.netCarbonImpactKgPerYear / 1000).toFixed(1)} tons</span>
                        <span class="impact-label">Net Carbon Impact/Year</span>
                    </div>
                </div>
                <div class="efficiency-meter">
                    <div class="efficiency-label">Carbon Efficiency: ${(result.carbonEfficiencyRatio * 100).toFixed(1)}%</div>
                    <div class="efficiency-bar">
                        <div class="efficiency-fill" style="width: ${Math.max(0, result.carbonEfficiencyRatio * 100)}%"></div>
                    </div>
                </div>
                <p class="project-capacity">
                    <i class="fas fa-seedling"></i> 
                    Project Capacity: ${result.projectCapacityHectares.toFixed(0)} hectares
                </p>
            </div>

            <div id="economics" class="metrics-content">
                <h4>Annual Cost Breakdown</h4>
                <div class="cost-breakdown">
                    <div class="cost-item">
                        <span class="cost-category">
                            <i class="fas fa-truck"></i> Transportation
                        </span>
                        <span class="cost-value">$${result.transportCostUsdPerYear.toLocaleString()}</span>
                    </div>
                    <div class="cost-item">
                        <span class="cost-category">
                            <i class="fas fa-hard-hat"></i> Labor
                        </span>
                        <span class="cost-value">$${result.laborCostUsdPerYear.toLocaleString()}</span>
                    </div>
                    <div class="cost-item">
                        <span class="cost-category">
                            <i class="fas fa-cogs"></i> Equipment
                        </span>
                        <span class="cost-value">$${result.equipmentCostUsdPerYear.toLocaleString()}</span>
                    </div>
                    <div class="cost-item total">
                        <span class="cost-category">
                            <i class="fas fa-calculator"></i> <strong>Total Annual Cost</strong>
                        </span>
                        <span class="cost-value"><strong>$${result.totalCostUsdPerYear.toLocaleString()}</strong></span>
                    </div>
                </div>
                <div class="cost-efficiency">
                    <div class="efficiency-metric">
                        <i class="fas fa-dollar-sign"></i>
                        <span class="metric-value">$${result.costPerTonCo2Removed.toFixed(0)}</span>
                        <span class="metric-label">Cost per Ton CO₂ Removed</span>
                    </div>
                </div>
            </div>
        `;
    }

    // Get sustainability grade class for styling
    getGradeClass(grade) {
        if (!grade) return 'grade-na';
        if (grade.startsWith('A')) return 'grade-a';
        if (grade.startsWith('B')) return 'grade-b';
        if (grade.startsWith('C')) return 'grade-c';
        if (grade.startsWith('D')) return 'grade-d';
        return 'grade-f';
    }

    // Format Breakdown Labels
    formatBreakdownLabel(key) {
        const labels = {
            soilPh: 'Soil pH',
            temperature: 'Temperature',
            rainfall: 'Rainfall',
            roadAccess: 'Road Access',
            transport: 'Transport Distance',
            infrastructure: 'Infrastructure',
            basaltAvailability: 'Basalt Availability'
        };
        return labels[key] || key.charAt(0).toUpperCase() + key.slice(1);
    }

    // Display Error
    displayError(message) {
        const container = document.getElementById('scoreResult');
        container.innerHTML = `
            <div class="error-display">
                <i class="fas fa-exclamation-triangle"></i>
                <h3>Calculation Error</h3>
                <p>${message}</p>
                <button class="btn-primary" onclick="document.getElementById('scoringForm').reset(); this.parentElement.parentElement.innerHTML = this.parentElement.parentElement.querySelector('.no-result').outerHTML;">
                    Try Again
                </button>
            </div>
        `;
    }
}

// API Testing Functions
function testEndpoint(endpoint) {
    const url = window.location.origin + endpoint;
    window.open(url, '_blank');
}

function testScoringEndpoint() {
    alert('Use the Score Calculator tab to test the scoring endpoint with a user-friendly form!');
    document.querySelector('[data-tab="calculator"]').click();
}

// Global function for metrics tab switching
function showMetricsTab(event, tabId) {
    // Remove active class from all tabs and contents
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.metrics-content').forEach(content => content.classList.remove('active'));
    
    // Add active class to clicked tab and corresponding content
    event.target.classList.add('active');
    document.getElementById(tabId).classList.add('active');
}

// Initialize Application
let app;
document.addEventListener('DOMContentLoaded', () => {
    app = new ERWApp();
});

// Add CSS for additional elements
const additionalStyles = `
.btn-secondary {
    background: #6c757d;
    color: white;
    border: none;
    padding: 0.8rem 1.5rem;
    border-radius: 8px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    margin-top: 1rem;
}

.btn-secondary:hover {
    background: #5a6268;
    transform: translateY(-2px);
}

.error-display {
    text-align: center;
    padding: 2rem;
    background: #ffe6e6;
    border-radius: 10px;
    border: 2px solid #ff9999;
}

.error-display i {
    font-size: 3rem;
    color: #dc3545;
    margin-bottom: 1rem;
}

.error-display h3 {
    color: #dc3545;
    margin-bottom: 1rem;
}

.map-popup h3 {
    margin-bottom: 0.5rem;
    color: #333;
}

.map-popup p {
    margin: 0.2rem 0;
    font-size: 0.9rem;
}

.map-popup .score {
    font-weight: bold;
    padding: 0.2rem 0.5rem;
    border-radius: 10px;
    color: white;
}

.map-popup .score.high { background: #4CAF50; }
.map-popup .score.medium { background: #FF9800; }
.map-popup .score.low { background: #f44336; }

.error, .no-results {
    text-align: center;
    padding: 2rem;
    color: #666;
    font-style: italic;
}
`;

// Inject additional styles
const styleSheet = document.createElement('style');
styleSheet.textContent = additionalStyles;
document.head.appendChild(styleSheet);