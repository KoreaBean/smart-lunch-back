package hello.lunchback.external.kakaoPay.dto.response;

import lombok.Data;

@Data
public class KakaopaySuccessResponseDto {

    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private String quantity;
    private String created_at;
    private String approved_at;
    private String payload;


}
