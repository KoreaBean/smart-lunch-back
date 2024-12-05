package hello.lunchback.storeManagement.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostStoreCreateRequestDto {

    private String businessNumber;
    private String storeDescription;
    private MultipartFile storeImg;
    private String storeName;


}
