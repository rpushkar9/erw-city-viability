package com.example.erw.controller;

import com.example.erw.dto.PagedSiteResponse;
import com.example.erw.dto.SiteScoreRequest;
import com.example.erw.dto.SiteScoreResponse;
import com.example.erw.model.Site;
import com.example.erw.repository.SiteRepository;
import com.example.erw.service.SiteScoringService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * REST Controller for ERW Site Operations
 * 
 * Provides endpoints for:
 * - Site listing with filtering, sorting, and pagination
 * - Comprehensive site scoring with detailed analysis
 * - Validation and error handling for all inputs
 * 
 * Security features:
 * - Whitelisted sort fields to prevent injection
 * - Input validation with detailed error messages
 * - Pagination limits to prevent resource exhaustion
 * 
 * @author ERW Site Assessment Platform
 * @version 2.0
 */
@RestController
@RequestMapping("/api")
public class SiteController {

    private final SiteRepository siteRepository;
    private final SiteScoringService siteScoringService;
    
    // Whitelisted sort fields to prevent arbitrary field access
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("score", "name", "id");

    public SiteController(SiteRepository siteRepository, SiteScoringService siteScoringService) {
        this.siteRepository = siteRepository;
        this.siteScoringService = siteScoringService;
    }

    @GetMapping("/sites")
    public ResponseEntity<PagedSiteResponse> listSites(
            @RequestParam(required = false) String region,
            @RequestParam(defaultValue = "score") String sort,
            @RequestParam(defaultValue = "desc") String dir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        // Validate and clamp page size between 1-100
        size = Math.max(1, Math.min(100, size));
        page = Math.max(0, page);
        
        // Validate sort field against whitelist
        if (!ALLOWED_SORT_FIELDS.contains(sort)) {
            sort = "score"; // fallback to default
        }

        var direction = "asc".equalsIgnoreCase(dir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        var sortObj = Sort.by(direction, sort);
        var pageable = PageRequest.of(page, size, sortObj);

        List<Site> results;
        long totalCount;
        
        if (region != null && !region.isBlank()) {
            results = siteRepository.findByRegionIgnoreCase(region, pageable);
            totalCount = siteRepository.countByRegionIgnoreCase(region);
        } else {
            var pageResult = siteRepository.findAll(pageable);
            results = pageResult.getContent();
            totalCount = pageResult.getTotalElements();
        }

        var response = new PagedSiteResponse(page, size, totalCount, results);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sitescore")
    public ResponseEntity<SiteScoreResponse> score(@Valid @RequestBody SiteScoreRequest req) {
        return ResponseEntity.ok(siteScoringService.scoreSite(req));
    }
}
