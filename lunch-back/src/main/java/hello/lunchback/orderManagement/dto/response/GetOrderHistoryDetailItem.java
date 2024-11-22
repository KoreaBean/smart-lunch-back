package hello.lunchback.orderManagement.dto.response;

import lombok.Data;

@Data
public class GetOrderHistoryDetailItem {

    private String menuName;
    private Integer menuPrice;
    private Integer quantity;
    private Integer totalPrice;


    public GetOrderHistoryDetailItem(String menuName, Integer menuPrice, Integer quantity) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.quantity = quantity;
        this.totalPrice = menuPrice * quantity;
    }
}
