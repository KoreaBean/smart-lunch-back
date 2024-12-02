package hello.lunchback.menuRecommendation.controller;

import hello.lunchback.menuRecommendation.dto.response.GetRecommendationResponseDto;
import hello.lunchback.menuRecommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    // 추천 메뉴 리스트 반환
    @GetMapping("/recommendation")
    public ResponseEntity<? super GetRecommendationResponseDto> recommendation (@AuthenticationPrincipal String email){
        ResponseEntity<? super  GetRecommendationResponseDto> result = recommendationService.findMenu(email);
        return result;
    }

}
