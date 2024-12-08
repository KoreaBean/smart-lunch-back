package hello.lunchback.orderManagement.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public class PostOrderRequestDto {
    List<MenuListItem> list;
    private Integer totalPrice;
}
