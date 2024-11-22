package hello.lunchback.orderManagement.dto.response;

import hello.lunchback.orderManagement.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetOrderHistoryDetailResponseDto {

    private List<GetOrderHistoryDetailItem> list;
    private Integer totalPrice;
    private String storeName;
    private String orderDate;
    private boolean isPay;
    private String waitingTime;
    private Integer myTurn;
    private String congestion;

    public GetOrderHistoryDetailResponseDto(OrderEntity order,List<GetOrderHistoryDetailItem> list, Integer totalPrice, String busy, Integer myWait, String predict) {
        this.list = list;
        this.totalPrice = totalPrice;
        this.storeName = order.getStore().getStoreName();
        this.orderDate = order.getOrderDate();
        this.isPay = order.isPay();
        this.waitingTime = busy;
        this.myTurn = myWait;
        this.congestion = predict;
    }


    public static GetOrderHistoryDetailResponseDto success(OrderEntity order,List<GetOrderHistoryDetailItem> list, Integer totalPrice, String busy, Integer myWait, String predict) {
        GetOrderHistoryDetailResponseDto result = new GetOrderHistoryDetailResponseDto(order, list, totalPrice, busy, myWait, predict);
        return result;
    }
}
