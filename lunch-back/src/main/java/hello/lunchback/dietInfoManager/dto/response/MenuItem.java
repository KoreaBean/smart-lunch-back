package hello.lunchback.dietInfoManager.dto.response;

import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuItem {

    private String menuName;
    private Integer calorie;
    private Integer carbs;
    private Integer protein;
    private Integer fat;


    public MenuItem(MenuEntity byMenuName) {
        this.menuName = byMenuName.getMenuName();
        this.calorie = byMenuName.getCalorie();
        this.carbs = byMenuName.getCarbs();
        this.protein = byMenuName.getProtein();
        this.fat = byMenuName.getFat();
    }
}
