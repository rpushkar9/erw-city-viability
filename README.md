# ERW City Viability Analysis Platform

> **Enhanced Rock Weathering Metropolitan Assessment Tool**  
> A comprehensive Spring Boot application for evaluating ERW project viability across global metropolitan areas using research-based 20-parameter assessment.

🌐 **[Live Demo: https://erw-city-viability-1.onrender.com/](https://erw-city-viability-1.onrender.com/)**

## 🌍 Overview

This platform provides scientific assessment of Enhanced Rock Weathering (ERW) potential for metropolitan areas and their agricultural hinterlands worldwide. Built with Spring Boot 3.3.2 and Java 21, it features an interactive web interface, comprehensive scoring algorithms, and RESTful API for ERW project evaluation.

**Key Capabilities:**
- **45 Pre-analyzed Metropolitan Areas** across 21 countries and 6 continents
- **20-Parameter Assessment Model** based on 2025 industry research
- **Interactive Web Interface** with global mapping and detailed analysis
- **RESTful API** for programmatic access and integration
- **Cost & Environmental Analysis** with CO₂ calculations

**🚀 Live Platform:** [https://erw-city-viability-1.onrender.com](https://erw-city-viability-1.onrender.com/)

## 🏗️ Architecture

**Backend:**
- **Framework:** Spring Boot 3.3.2 (Java 21)
- **Database:** PostgreSQL with JPA/Hibernate
- **Validation:** Jakarta Validation with comprehensive constraints
- **Security:** Input validation, SQL injection prevention, resource limits

**Frontend:**
- **Interface:** Modern HTML5/CSS3/JavaScript
- **Mapping:** Leaflet.js 1.9.4 for interactive maps
- **Visualization:** Chart.js for data presentation
- **Styling:** Responsive design with glass-morphism effects

## 🚀 Quick Start

### Prerequisites
- **Java 21** (OpenJDK or Oracle)
- **Docker Desktop** (for PostgreSQL)
- **Git** (for cloning)

### Installation

```bash
# 1. Clone repository
git clone https://github.com/rpushkar9/erw-city-viability.git
cd erw-city-viability

# 2. Start PostgreSQL database
docker compose up -d

# 3. Run application
./mvnw spring-boot:run

# 4. Access web interface
# http://localhost:8080
```

### Alternative: Native PostgreSQL
```bash
# If you have PostgreSQL installed locally
export DB_URL=jdbc:postgresql://localhost:5432/erwdb
export DB_USER=your_username
export DB_PASS=your_password

./mvnw spring-boot:run
```

## 📊 Core Features

### 1. **Metropolitan Area Rankings**
- Global dashboard with 45 metropolitan areas
- Sortable by ERW score, name, or region
- Filterable by country/region
- Statistical overview with averages and distribution

### 2. **Interactive Global Map**
- Real-time metropolitan area visualization
- Color-coded by ERW potential (High: 0.7-1.0, Medium: 0.4-0.69, Low: 0.0-0.39)
- Click for detailed analysis panels
- Responsive design for all devices

### 3. **Metropolitan ERW Calculator**
- **20-parameter assessment model:**

**Basic Parameters (8):**
- Geographical coordinates (latitude/longitude)
- Annual rainfall (mm)
- Soil pH (0-14 scale)
- Average temperature (°C)
- Road access distance (km)
- Basalt transport distance (km)
- Basalt availability index (0-1)
- Infrastructure quality index (0-1)

**Economic Parameters (4):**
- Agricultural land scale (hectares, up to 20M)
- Population density (people/km²)
- Energy cost ($/kWh)
- Agricultural labor cost ($/hour)

**Advanced Parameters (8) - Based on 2025 Research:**
- Annual rainfall variability (mm std deviation)
- Soil organic carbon percentage (%)
- Elevation (meters)
- Regulatory stability index (0-1)
- Soil moisture percentage (10-90%)
- Carbon market accessibility (0-1)
- Soil cation exchange capacity (meq/100g)
- Monitoring capability index (0-1)

### 4. **Analysis Results**
- **ERW Viability Score** (0.0-1.0 scale)
- **Cost Analysis** ($/ton basalt with breakdown)
- **CO₂ Impact** (emissions vs. removal calculations)
- **Sustainability Grade** (A+ to F rating)
- **Implementation Recommendations**

## 🌐 API Documentation

### Base URLs
```
# Local Development
http://localhost:8080/api

# Live Demo
https://erw-city-viability-1.onrender.com/api
```

### Endpoints

#### 1. List Metropolitan Areas
```http
GET /api/sites
```

**Query Parameters:**
- `region` (optional) - Filter by region (e.g., "California-USA")
- `sort` (optional) - Sort by: `score`, `name`, `id` (default: `score`)
- `dir` (optional) - Direction: `asc`, `desc` (default: `desc`)
- `page` (optional) - Page number (default: `0`)
- `size` (optional) - Page size 1-100 (default: `20`)

**Examples:**
```bash
# Local development
curl "http://localhost:8080/api/sites?region=California-USA&sort=score&dir=desc"

# Live demo
curl "https://erw-city-viability-1.onrender.com/api/sites?region=California-USA&sort=score&dir=desc"
```

**Response:**
```json
{
  "page": 0,
  "size": 20,
  "totalCount": 45,
  "sites": [
    {
      "id": 1,
      "name": "Des Moines",
      "region": "Iowa-USA",
      "latitude": 41.5868,
      "longitude": -93.6250,
      "score": 0.94
    }
  ]
}
```

#### 2. Calculate ERW Score
```http
POST /api/sitescore
Content-Type: application/json
```

**Request Body:**
```json
{
  "latitude": 41.5868,
  "longitude": -93.6250,
  "rainfallMm": 1200,
  "soilPh": 6.8,
  "avgTemperatureC": 10.5,
  "distanceToRoadKm": 2.0,
  "basaltTransportDistanceKm": 150,
  "basaltAvailabilityIndex": 0.8,
  "infrastructureQualityIndex": 0.85,
  "agriculturalLandHectares": 1500000,
  "populationDensityPerKm2": 150,
  "energyCostPerKWh": 0.12,
  "laborCostPerHour": 18.5,
  "annualRainfallVariability": 200,
  "soilOrganicCarbonPercent": 3.5,
  "elevationMeters": 400,
  "regulatoryStabilityIndex": 0.9,
  "soilMoisturePercent": 45,
  "carbonMarketAccessibility": 0.85,
  "soilCecMeqPer100g": 15,
  "monitoringCapabilityIndex": 0.8
}
```

**Response:**
```json
{
  "score": 0.94,
  "grade": "A+",
  "costPerTon": 125.50,
  "co2RemovalKgPerTon": 333,
  "co2EmissionsKgPerTon": 45,
  "netCo2BenefitKgPerTon": 288,
  "breakdown": {
    "environmental": 0.92,
    "logistics": 0.88,
    "economic": 0.85,
    "advanced": 0.89
  }
}
```

#### 3. Service Health
```http
GET /api/health
```

#### 4. Service Info
```http
GET /api/info
```

## 🗂️ Global Coverage

**45 Metropolitan Areas across 21 Countries:**

**North America (14):**
- 🇺🇸 USA: Des Moines, Fresno, Kansas City, Lincoln, Fargo, Stockton, Bakersfield, Grand Island, Minot
- 🇨🇦 Canada: Winnipeg, Calgary, Saskatoon, Regina, Lethbridge

**Asia (9):**
- 🇮🇳 India: Bangalore, Hyderabad, Ludhiana, Nashik, Mysore
- 🇨🇳 China: Jinan, Beijing Rural
- 🇯🇵 Japan: Sendai
- 🇰🇷 South Korea: Suwon

**Europe (6):**
- 🇫🇷 France: Toulouse, Toulouse Rural
- 🇪🇸 Spain: Lleida
- 🇺🇦 Ukraine: Kiev
- 🇩🇪 Germany: Munich Rural
- 🇷🇺 Russia: Krasnodar, Novosibirsk Rural

**South America (6):**
- 🇧🇷 Brazil: São Paulo Interior, Piracicaba, Londrina
- 🇦🇷 Argentina: Córdoba, Mendoza, Rosario

**Africa (4):**
- 🇰🇪 Kenya: Nairobi
- 🇿🇦 South Africa: Johannesburg Rural
- 🇲🇦 Morocco: Casablanca Rural
- 🇳🇬 Nigeria: Kano

**Oceania (3):**
- 🇦🇺 Australia: Brisbane, Perth
- 🇳🇿 New Zealand: Palmerston North

**Asia-Europe Bridge (1):**
- 🇹🇷 Turkey: Ankara Rural

**Southeast Asia (1):**
- 🇹🇭 Thailand: Bangkok Rural

## 🧪 Development

### Project Structure
```
src/
├── main/
│   ├── java/com/example/erw/
│   │   ├── Application.java              # Main application
│   │   ├── config/DatabaseConfig.java    # DB configuration
│   │   ├── controller/
│   │   │   ├── HomeController.java       # Service info & health
│   │   │   └── SiteController.java       # Main API endpoints
│   │   ├── dto/                          # Data Transfer Objects
│   │   │   ├── PagedSiteResponse.java
│   │   │   ├── SiteScoreRequest.java     # 20-parameter input
│   │   │   └── SiteScoreResponse.java
│   │   ├── model/Site.java               # JPA entity
│   │   ├── repository/SiteRepository.java
│   │   ├── service/
│   │   │   ├── SiteScoringService.java
│   │   │   └── impl/SiteScoringServiceImpl.java  # Core algorithm
│   │   └── exception/GlobalExceptionHandler.java
│   └── resources/
│       ├── static/                       # Web interface
│       │   ├── index.html               # Single-page application
│       │   ├── css/styles.css           # Responsive styling
│       │   └── js/app.js                # Interactive features
│       ├── application.yml              # Configuration
│       └── data.sql                     # 45 metropolitan areas
└── test/java/                           # Unit tests
```

### Running Tests
```bash
./mvnw test
```

### Building
```bash
# Build JAR
./mvnw clean package

# Build Docker image (requires Dockerfile)
docker build -t erw-city-viability .
```

## 🔧 Configuration

### Environment Variables
- `PORT` - Server port (default: 8080)
- `DATABASE_URL` - PostgreSQL JDBC URL
- `DB_USER` - Database username (if not in URL)
- `DB_PASS` - Database password (if not in URL)

### Application Properties (`application.yml`)
```yaml
server:
  port: ${PORT:8080}
spring:
  datasource:
    # URL configured via DatabaseConfig.java
    hikari:
      connection-timeout: 60000
      maximum-pool-size: 5
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
  sql:
    init:
      mode: always
      continue-on-error: true
```

## 🚀 Deployment

### Cloud Deployment (Render/Heroku)
1. Set `DATABASE_URL` environment variable
2. Application automatically configures database connection
3. Uses embedded Tomcat (no external server needed)
4. Health check available at `/api/health`

### Docker Deployment
```bash
# Using docker-compose
docker compose up -d

# Or manually
docker run -d --name postgres -e POSTGRES_DB=erwdb -p 5432:5432 postgres:15-alpine
docker run -d --name erw-app -p 8080:8080 -e DATABASE_URL=jdbc:postgresql://host:5432/erwdb erw-city-viability
```

## 📈 Technical Specifications

- **Response Time:** <200ms for scoring calculations
- **Concurrent Users:** Supports 100+ simultaneous users  
- **Memory Usage:** ~512MB typical Spring Boot footprint
- **Storage:** <100MB including static assets
- **Database:** 45 metropolitan areas, expandable design
- **API Rate Limiting:** Input validation and pagination prevent abuse
- **Security:** SQL injection prevention, input sanitization

## 🤝 Contributing

**Contact Information:**
- **Developer:** Pushkar Rimmalapudi
- **Email:** rpushkar@uw.edu
- **GitHub:** [@rpushkar9](https://github.com/rpushkar9)

**Development Guidelines:**
1. Follow Spring Boot best practices
2. Maintain comprehensive input validation
3. Include unit tests for new features
4. Update API documentation for changes
5. Ensure responsive web design compatibility

## 📄 License

This project is open source and available under standard terms for research and educational purposes.

---

**🌍 ERW City Viability Analysis Platform** - Advancing Enhanced Rock Weathering through data-driven metropolitan assessment and global research collaboration.