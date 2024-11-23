package hello.lunchback.orderManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryItem {
    private String orderDate;
    private Integer orderId;
    private String storeImage;
    private String storeName;
    private List<String> menuName = new ArrayList<>();
    private Integer totalPrice = 0;
    // 주문 날짜, 가게 사진, 가게명, 메뉴, 총 가격

}
