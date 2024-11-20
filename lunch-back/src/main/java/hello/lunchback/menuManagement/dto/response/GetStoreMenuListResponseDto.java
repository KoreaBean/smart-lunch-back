package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetStoreMenuListResponseDto extends ResponseDto {

    public List<GetStoreMenuItem> list = new ArrayList<GetStoreMenuItem>();

    public GetStoreMenuListResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public GetStoreMenuListResponseDto(List<GetStoreMenuItem> item) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.list = item;

    }

    public static ResponseEntity<? super GetStoreMenuListResponseDto> success(List<GetStoreMenuItem> item) {
        GetStoreMenuListResponseDto result = new GetStoreMenuListResponseDto(item);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
