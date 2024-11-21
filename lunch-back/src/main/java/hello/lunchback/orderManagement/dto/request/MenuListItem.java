package hello.lunchback.orderManagement.dto.request;

import lombok.Data;

@Data
public class MenuListItem {

    private String menuName;
    private Integer price;
    private Integer quantity;

}
