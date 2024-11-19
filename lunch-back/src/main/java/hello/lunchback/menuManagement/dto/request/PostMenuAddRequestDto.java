package hello.lunchback.menuManagement.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostMenuAddRequestDto {

    private Integer storeId;
    private String menuName;
    private String menuDescription;
    private Integer menuPrice;
    private MultipartFile menuImage;


}
