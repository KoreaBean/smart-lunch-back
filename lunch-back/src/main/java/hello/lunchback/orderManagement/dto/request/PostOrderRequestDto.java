package hello.lunchback.orderManagement.dto.request;

import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class PostOrderRequestDto {
    List<MenuListItem> list;
    private Integer totalPrice;
}
