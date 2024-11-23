package hello.lunchback.orderManagement.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetOrderHistoryResponseDto {

    private String code;
    private List<OrderHistoryItem> item = new ArrayList<>();
    // 주문 날짜, 가게 사진, 가게명, 메뉴, 총 가격


    public GetOrderHistoryResponseDto(String code,List<OrderHistoryItem> list) {
        this.code = code;
        this.item = list;
    }

    public GetOrderHistoryResponseDto(String code) {
        this.code = code;
    }

    public static GetOrderHistoryResponseDto databaseErr(){
        GetOrderHistoryResponseDto result = new GetOrderHistoryResponseDto("DBE");
        return result;
    }


    public static GetOrderHistoryResponseDto notExistedUser() {
        GetOrderHistoryResponseDto result = new GetOrderHistoryResponseDto("NU");
        return result;
    }

    public static GetOrderHistoryResponseDto success(List<OrderHistoryItem> historyItems) {
        GetOrderHistoryResponseDto result = new GetOrderHistoryResponseDto("SU",historyItems);
        return result;
    }
}
