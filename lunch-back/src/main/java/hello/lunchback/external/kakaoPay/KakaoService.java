package hello.lunchback.external.kakaoPay;

import hello.lunchback.external.kakaoPay.dto.request.KakaopayRequestDto;
import hello.lunchback.external.kakaoPay.dto.request.KakaopaySuccessRequestDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopaySuccessResponseDto;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class KakaoService {

    private final MemberRepository memberRepository;
    private static HashMap<String, Map<String,String>> list = new HashMap<>();

    private WebClient webClient;
    //@Value("${kakaopay.secret_key_dev}")
    private String secret_key = " " +
            "DEV278FC6A197DD5A89664291D5BDC43B847776C";

    public KakaoService(WebClient.Builder webclient, MemberRepository memberRepository) {
        this.webClient = webclient.baseUrl("https://open-api.kakaopay.com/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "SECRET_KEY" + secret_key)
                .build();
        this.memberRepository = memberRepository;
    }

    public KakaopayResponseDto requestPayment (KakaopayRequestDto requestDto){
        //requestDto.setCid(cid);
        try {
            KakaopayResponseDto result = webClient.post()
                    .uri("online/v1/payment/ready")
                    .bodyValue(requestDto)
                    .retrieve()
                    .bodyToMono(KakaopayResponseDto.class)
                    .block();

            Map<String, String> data = payInfomation(result, requestDto);
            list.put(requestDto.getPartner_user_id(),data);
            result.setNext_redirect_pc_url(result.getNext_redirect_pc_url() +"?partner_user_id=" + requestDto.getPartner_user_id());
            result.setNext_redirect_mobile_url(result.getNext_redirect_mobile_url()+"?partner_user_id=" + requestDto.getPartner_user_id());
            return result;
        }catch (WebClientResponseException e){
            e.printStackTrace();
            throw new RuntimeException("카카오페이 요청 실패: " + e.getResponseBodyAsString());
        }
    }

    private Map<String,String> payInfomation(KakaopayResponseDto result, KakaopayRequestDto dto) {
        Map<String,String> pay = new HashMap<>();
        pay.put("tid", result.getTid());
        pay.put("cid","TC0ONETIME");
        pay.put("partner_order_id",dto.getPartner_order_id());
        pay.put("partner_user_id",dto.getPartner_user_id());

        return pay;
    }

    public PostOrderResponseDto success(String token) {
        try {
            for (String userId : list.keySet()) {
                KakaopaySuccessRequestDto request = new KakaopaySuccessRequestDto(token,list.get(userId));

                KakaopaySuccessResponseDto result = webClient.post()
                        .uri("/online/v1/payment/approve")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(KakaopaySuccessResponseDto.class)
                        .block();
                list.remove(userId);
            }
        }catch (Exception e){
         e.printStackTrace();
     }
     return PostOrderResponseDto.success();
    }
}
