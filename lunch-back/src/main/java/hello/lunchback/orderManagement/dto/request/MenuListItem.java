package hello.lunchback.orderManagement.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuListItem {

    private String menuName;
    private Integer price;
    private Integer quantity;

}
