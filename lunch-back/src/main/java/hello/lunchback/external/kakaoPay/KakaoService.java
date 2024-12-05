package hello.lunchback.external.kakaoPay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.lunchback.external.kakaoPay.dto.request.KakaopayRequestDto;
import hello.lunchback.external.kakaoPay.dto.request.KakaopaySuccessRequestDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopaySuccessResponseDto;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.orderManagement.dto.OrderStatus;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.OrderNotificationDto;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.orderManagement.repository.OrderRepository;
import hello.lunchback.orderManagement.service.impl.OrderServiceImpl;
import hello.lunchback.waitManagement.WaitingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class KakaoService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final WaitingManager waitingManager;
    private final SimpMessagingTemplate messagingTemplate;
    private static HashMap<String, Map<String,String>> list = new HashMap<>();

    private WebClient webClient;
    //@Value("${kakaopay.secret_key_dev}")
    private String secret_key = " " +
            "DEV278FC6A197DD5A89664291D5BDC43B847776C";

    public KakaoService(WebClient.Builder webclient, MemberRepository memberRepository, OrderRepository orderRepository,
                        WaitingManager waitingManager, SimpMessagingTemplate messagingTemplate) {
        this.webClient = webclient.baseUrl("https://open-api.kakaopay.com/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "SECRET_KEY" + secret_key)
                .build();
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.waitingManager = waitingManager;
        this.messagingTemplate = messagingTemplate;
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

    @Transactional
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
                // 성공하면 orderId 로 orderEntity 찾아서 isPay true 하고 대기열 넣고 알람 전송
                OrderEntity orderEntity = orderRepository.findByOrderId(Integer.valueOf(result.getPartner_order_id()));
                orderEntity.setPay(true);
                waitingManager.add(orderEntity.getStore().getStoreId(),orderEntity.getOrderId());
                Long totalPrice = setTotalPrice(orderEntity);
                sendToStoreAlam(orderEntity.getOrderId(), orderEntity,totalPrice,orderEntity.getStatus());

            }
        }catch (Exception e){
         e.printStackTrace();
     }
     return PostOrderResponseDto.success();
    }

    private Long setTotalPrice(OrderEntity orderEntity) {
        // orderEntity의 List<orderDetailEntity> 가져와서 모든 디테일 돌려서 price 총합 구해서 반환
        return orderEntity.getOrderDetail()
                .stream()
                .mapToLong(menu -> menu.getMenuPrice().longValue() * menu.getQuantity())
                .sum();
    }

    private void sendToStoreAlam(Integer orderId, OrderEntity orderEntity, Long totalAmount, OrderStatus status) throws JsonProcessingException {
        OrderNotificationDto orderNotificationDto = new OrderNotificationDto(orderId, orderEntity.getOrderDate(), totalAmount, status);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(orderNotificationDto);

        Integer storeId = orderEntity.getStore().getStoreId();

        messagingTemplate.convertAndSend("/room/"+storeId,jsonMessage);

    }

//
//    private void sendToStoreAlam(PostOrderRequestDto dto, OrderEntity orderEntity) throws JsonProcessingException {
//        // 보낼 가게 멤버 id , 총 가격,
//        OrderNotificationDto notificationDto = new OrderNotificationDto(orderEntity.getOrderId(), orderEntity.getOrderDate(), dto.getTotalPrice());
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonMessage = objectMapper.writeValueAsString(notificationDto);
//        //messagingTemplate.convertAndSend("/room/"+ result.store().getMember().getMemberId(),jsonMessage);
//        messagingTemplate.convertAndSend("/room/"+1,jsonMessage);
//    }

}
