package hello.lunchback.menuManagement.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostMenuAddRequestDto {

    private Long menuId;
    private String name;
    private String description;
    private Integer price;
    private MultipartFile menuImg;
    private Integer calorie;
    private Integer carbs;
    private Integer protein;
    private Integer fat;
    private Integer isSoldOut;



}
