package hello.lunchback.menuRecommendation.service;

import hello.lunchback.menuRecommendation.dto.response.GetRecommendationResponseDto;
import org.springframework.http.ResponseEntity;

public interface RecommendationService {
    ResponseEntity<? super GetRecommendationResponseDto> findMenu(String email);
}
