package hello.lunchback.orderManagement.dto.response;

import hello.lunchback.orderManagement.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    private Integer orderId;
    // 주문 날짜
    private String date;
    // 가게 이름
    private String storeName;
    // 메뉴 설명
    //private String menuSummary;
    // 갯수
    private Integer amount;
    // 주문 시간
    private String orderDate;
    // 예상 시간
    private String estimatedTime;
    // 웨이팅 시간
    private String waitingTime;
    // 혼잡도 ?
    private String crowdLevel;

    private List<OrderItemV2> items = new ArrayList<>();
    private Long totalPrice;


    public Order(OrderEntity orderEntity) {
        this.orderId = orderEntity.getOrderId();
        this.date = orderEntity.getOrderDate();
        this.orderDate = orderEntity.getOrderDate();
        this.storeName = orderEntity.getStore().getStoreName();


    }
}
