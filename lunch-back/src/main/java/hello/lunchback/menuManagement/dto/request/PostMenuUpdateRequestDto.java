package hello.lunchback.menuManagement.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostMenuUpdateRequestDto {

    private Long menuId;
    private Long StoreId;
    private String name;
    private String description;
    private Integer price;
    private String menuImg;
    private Integer calorie;
    private Integer carbs;
    private Integer protein;
    private Integer fat;
    private Integer isSoldOut;

}

