package hello.lunchback.menuRecommendation.service;

import hello.lunchback.menuRecommendation.dto.response.GetRecommendationResponseDto;

public interface RecommendationService {
    GetRecommendationResponseDto findMenu(String email);
}
