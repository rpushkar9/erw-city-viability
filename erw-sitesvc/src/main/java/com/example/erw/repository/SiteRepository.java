package com.example.erw.repository;

import com.example.erw.model.Site;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findByRegionIgnoreCase(String region, Sort sort);
    List<Site> findByRegionIgnoreCase(String region, Pageable pageable);
    long countByRegionIgnoreCase(String region);
}
