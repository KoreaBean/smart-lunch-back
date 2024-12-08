package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import hello.lunchback.menuManagement.entity.MenuEntity;
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
public class GetStoreMenuListResponseDto extends ResponseDto{

    public List<MenuEntity> list = new ArrayList<>();


    public GetStoreMenuListResponseDto(List<MenuEntity> menuList) {
        super(ResponseCode.SUCCESS,ResponseMessage.SUCCESS);
        this.list = menuList;

    }


    public static ResponseEntity<? super GetStoreMenuListResponseDto> notExistedMenu() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MENU, ResponseMessage.NOT_EXISTED_MENU);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

    public static ResponseEntity<? super GetStoreMenuListResponseDto> success(List<MenuEntity> menuList) {
        GetStoreMenuListResponseDto result = new GetStoreMenuListResponseDto(menuList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
