package hello.lunchback.menuRecommendation.dto.response;

import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetRecommendationResponseDto {

    private final List<MenuEntity> list = new ArrayList<>();

    public GetRecommendationResponseDto(List<MenuEntity> allMenu) {
        this.list.addAll(allMenu);
    }

    public static GetRecommendationResponseDto success(List<MenuEntity> allMenu) {
        GetRecommendationResponseDto result = new GetRecommendationResponseDto(allMenu);
        return result;
    }
}
