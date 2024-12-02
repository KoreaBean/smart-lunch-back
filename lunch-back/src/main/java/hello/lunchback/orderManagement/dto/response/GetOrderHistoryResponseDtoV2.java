package hello.lunchback.orderManagement.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetOrderHistoryResponseDtoV2 {
    private Integer orderId;
    // 주문 날짜
    private String date;
    // 가게 이름
    private String storeName;
    // ?
    private String menuSummary;
    // 갯수
    private Integer amount;

    private String orderDate;
    private String estimatedTime;
    private String waitingTime;
    private String crowdLevel;

    private List<OrderItemV2> items = new ArrayList<>();
    private Long totalPrice;

}
