package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetStoreOrderDetailResponseDto extends ResponseDto {

    private List<OrderDetailEntity> menuList = new ArrayList<>();
    private String orderDate;
    private String consumerName;
    private String consumerPhoneNumber;

    public GetStoreOrderDetailResponseDto(StoreOrderDetailDto dto) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.menuList = dto.getMenuList();
        this.orderDate = dto.getOrderDate();
        this.consumerName = dto.getConsumerName();
        this.consumerPhoneNumber = dto.getConsumerPhoneNumber();
    }

    public static ResponseEntity<? super GetStoreOrderDetailResponseDto> success(StoreOrderDetailDto dto) {
        GetStoreOrderDetailResponseDto result = new GetStoreOrderDetailResponseDto(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<? super GetStoreOrderDetailResponseDto> notExistedUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

    public static ResponseEntity<? super GetStoreOrderDetailResponseDto> notExistedStore() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_STORE, ResponseMessage.NOT_EXISTED_STORE);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }
}
