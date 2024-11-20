package hello.lunchback.menuManagement.dto.request;

import lombok.Data;

@Data
public class PostMenuUpdateRequestDto {

    private String menuName;
    private String menuDescription;
    private Integer menuPrice;
    private String nutrients;

}

