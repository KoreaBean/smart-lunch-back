package hello.lunchback.external.kakaoPay.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaopayRequestDto {

    // 가맹점 코드
    private String cid;
    // 주문번호
    private String partner_order_id;
    // 사용자 ID
    private String partner_user_id;
    // 상품명
    private String item_name;
    // 수량
    private Integer quantity;
    // 총 결제 비용
    private Integer total_amount;
    // 비과세 금액
    private Integer tax_free_amount;
    // 결제 성공시 redirect URL
    private String approval_url;
    // 결제 취소 시 redirect URL
    private String cancel_url;
    // 결제 실패 시 redirect URL
    private String fail_url;

    public void ready() {

    }
}
