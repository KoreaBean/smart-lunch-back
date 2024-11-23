package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetStoreMenuListResponseDto {

    public List<GetStoreMenuItem> list = new ArrayList<GetStoreMenuItem>();


    public GetStoreMenuListResponseDto(List<GetStoreMenuItem> item) {
        this.list = item;

    }

    public static GetStoreMenuListResponseDto success(List<GetStoreMenuItem> item) {
        GetStoreMenuListResponseDto result = new GetStoreMenuListResponseDto(item);
        return result;
    }
}
