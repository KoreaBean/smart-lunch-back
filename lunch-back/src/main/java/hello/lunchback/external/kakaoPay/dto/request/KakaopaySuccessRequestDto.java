package hello.lunchback.external.kakaoPay.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class KakaopaySuccessRequestDto {

    private String cid;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;

    public KakaopaySuccessRequestDto(String token, Map<String, String> data) {
        this.cid = data.get("cid");
        this.tid = data.get("tid");
        this.partner_order_id = data.get("partner_order_id");
        this.partner_user_id = data.get("partner_user_id");
        this.pg_token = token;
    }
}
