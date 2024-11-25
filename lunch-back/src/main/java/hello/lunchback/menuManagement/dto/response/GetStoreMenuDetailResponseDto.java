package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;

@Data
public class GetStoreMenuDetailResponseDto {

    private Integer menuId;
    private String menuName;
    private String menuDescription;
    private String menuImg;
    private Integer price;
    private Integer calorie;
    private Integer carbs;
    private Integer fat;
    private Integer protein;
    private Integer isSoldOut;

    public GetStoreMenuDetailResponseDto(MenuEntity menu) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuDescription = menu.getMenuDescription();
        this.menuImg = menu.getMenuImage();
        this.price = menu.getMenuPrice();
        this.calorie = menu.getCalorie();
        this.carbs = menu.getCarbs();
        this.fat = menu.getFat();
        this.protein = menu.getProtein();
        this.isSoldOut = menu.getIsSoldOut();
    }

    public static GetStoreMenuDetailResponseDto success(MenuEntity menuEntity) {
        GetStoreMenuDetailResponseDto result = new GetStoreMenuDetailResponseDto(menuEntity);
        return result;
    }
}
