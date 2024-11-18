package hello.lunchback.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ResponseDto {
    private String code;
    private String message;

    public ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<ResponseDto> databaseError(){
        ResponseDto result = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}
