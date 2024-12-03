package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;

@Data
public class MenuInfoItem {
    private Integer menuId;
    private String menuName;
    private Integer price;
    private String menuImg;

    public MenuInfoItem(MenuEntity menuEntity, String menuImg) {
        this.menuId = menuEntity.getMenuId();
        this.menuName = menuEntity.getMenuName();
        this.price = menuEntity.getMenuPrice();
        this.menuImg = menuImg;
    }
}
