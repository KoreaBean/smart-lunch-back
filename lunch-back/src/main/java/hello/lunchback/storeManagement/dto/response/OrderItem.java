package hello.lunchback.storeManagement.dto.response;

import lombok.Data;

@Data
public class OrderItem {
    private Integer orderId;
    private String orderDate;
    private Integer totalPrice;
}
