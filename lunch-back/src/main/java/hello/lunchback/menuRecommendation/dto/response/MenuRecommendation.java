package hello.lunchback.menuRecommendation.dto.response;

import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;

@Data
public class MenuRecommendation {

    private Integer storeId;
    private Integer menuId;
    private String menuName;
    private String menuImage;
    private String menuDescription;
    private Integer menuPrice;
    private Integer calorie;
    private Integer carbs;
    private Integer protein;
    private Integer fat;
    private Integer isSoldOut;

    public MenuRecommendation(MenuEntity menu, String menuImg) {
        this.storeId = menu.getStore().getStoreId();
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuImage = menuImg;
        this.menuDescription =menu.getMenuDescription();
        this.menuPrice = menu.getMenuPrice();
        this.calorie = menu.getCalorie();
        this.carbs = menu.getCarbs();
        this.protein = menu.getProtein();
        this.fat = menu.getFat();
        this.isSoldOut = menu.getIsSoldOut();

    }
}
