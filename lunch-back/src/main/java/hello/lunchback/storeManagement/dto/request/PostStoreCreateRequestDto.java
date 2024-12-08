package hello.lunchback.storeManagement.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostStoreCreateRequestDto {

    private String businessNumber;
    private String storeDescription;
    private MultipartFile storeImg;
    private String storeName;


}
