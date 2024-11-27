package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;

@Data
public class MenuInfoItem {
    private Integer menuId;
    private String menuName;
    private Integer price;

    public MenuInfoItem(MenuEntity menuEntity) {
        this.menuId = menuEntity.getMenuId();
        this.menuName = menuEntity.getMenuName();
        this.price = menuEntity.getMenuPrice();
    }
}
