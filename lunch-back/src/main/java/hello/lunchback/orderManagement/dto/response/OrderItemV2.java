package hello.lunchback.orderManagement.dto.response;

import lombok.Data;

@Data
public class OrderItemV2 {

    private String name;
    private Integer unitPrice;
    private Integer quantity;
    private Integer totalPrice;
}
