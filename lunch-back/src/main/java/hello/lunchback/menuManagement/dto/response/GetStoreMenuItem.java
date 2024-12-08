package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetStoreMenuItem {

    private Integer menuId;
    private String menuName;
    private String menuImage;
    private String menuDescription;
    private String menuImg;
    private Integer price;
    private Integer calorie;
    private Integer carbs;
    private Integer fat;
    private Integer protein;
    private Integer isSoldOut;
    

    public GetStoreMenuItem(MenuEntity menuEntity, String image) {
        this.menuId = menuEntity.getMenuId();
        this.menuName = menuEntity.getMenuName();
        this.menuImage = image;
        this.menuDescription = menuEntity.getMenuDescription();
        this.price = menuEntity.getMenuPrice();
    }

}
