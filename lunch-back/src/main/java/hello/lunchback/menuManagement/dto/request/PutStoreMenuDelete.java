package hello.lunchback.menuManagement.dto.request;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PutStoreMenuDelete extends ResponseDto {


    public PutStoreMenuDelete() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<? super PutStoreMenuDelete> success() {
        PutStoreMenuDelete result = new PutStoreMenuDelete();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
