package com.example.erw.service;

import com.example.erw.dto.SiteScoreRequest;
import com.example.erw.dto.SiteScoreResponse;

public interface SiteScoringService {
    SiteScoreResponse scoreSite(SiteScoreRequest req);
}
