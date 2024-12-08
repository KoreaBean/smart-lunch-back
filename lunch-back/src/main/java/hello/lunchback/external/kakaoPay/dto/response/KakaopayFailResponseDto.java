package hello.lunchback.external.kakaoPay.dto.response;

import lombok.Data;

@Data
public class KakaopayFailResponseDto {

    private Integer error_code;
    private String error_message;

}
