package com.example.erw.model;

import jakarta.persistence.*;

@Entity
@Table(name = "site",
       uniqueConstraints = @UniqueConstraint(columnNames = {"name","region"}))
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String region;

    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private Double score = 0.0;

    public Site() {}

    public Site(String name, String region, Double latitude, Double longitude, Double score) {
        this.name = name;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
