package hello.lunchback.orderManagement.dto.response;

import hello.lunchback.orderManagement.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    // 가게 이미지
    private String storeImg;
    // 메뉴 설명
    //private String menuSummary;
    // 갯수
    //private Integer amount;
    // 주문 시간
    private String orderDate;
    // 예상 시간
    private String estimatedTime;
    // 내 순서
    private String waiting;
    // 혼잡도 ?
    private String crowdLevel;

    private List<OrderItemV2> items = new ArrayList<>();
    private Long totalPrice;


    public Order(OrderEntity orderEntity, String storeImg, Integer myWait, String waitingTime, String busy, Long totalPrice, List<OrderItemV2> items) {
        this.orderId = orderEntity.getOrderId();
        this.date = orderEntity.getOrderDate();
        this.storeName = orderEntity.getStore().getStoreName();
        this.storeImg = storeImg;
        this.orderDate = orderEntity.getOrderDate();
        this.estimatedTime = waitingTime;
        this.waiting = String.valueOf(myWait);
        this.crowdLevel = busy;
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
