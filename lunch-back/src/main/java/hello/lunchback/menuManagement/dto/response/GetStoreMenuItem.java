package hello.lunchback.menuManagement.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hello.lunchback.menuManagement.entity.MenuEntity;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GetStoreMenuItem {

    private Integer menuId;
    private String menuName;
    private String menuImage;
    private String menuDescription;
    private Integer menuPrice;

    public GetStoreMenuItem(MenuEntity menuEntity, String image) {
        this.menuId = menuEntity.getMenuId();
        this.menuName = menuEntity.getMenuName();
        this.menuImage = image;
        this.menuDescription = menuEntity.getMenuDescription();
        this.menuPrice = menuEntity.getMenuPrice();
    }

}
