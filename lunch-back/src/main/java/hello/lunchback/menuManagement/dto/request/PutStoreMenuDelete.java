package hello.lunchback.menuManagement.dto.request;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class PutStoreMenuDelete {


    private String code;

    public PutStoreMenuDelete(String code) {
        this.code = code;
    }

    public static PutStoreMenuDelete success() {
        PutStoreMenuDelete result = new PutStoreMenuDelete("SU");
        return result;
    }


}
