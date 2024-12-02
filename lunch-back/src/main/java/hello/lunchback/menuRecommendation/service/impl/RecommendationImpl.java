package hello.lunchback.menuRecommendation.service.impl;

import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.menuManagement.repository.MenuRepository;
import hello.lunchback.menuRecommendation.dto.response.GetRecommendationResponseDto;
import hello.lunchback.menuRecommendation.service.RecommendationService;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.orderManagement.repository.OrderDetailRepository;
import hello.lunchback.orderManagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationImpl implements RecommendationService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public ResponseEntity<? super  GetRecommendationResponseDto> findMenu(String email) {
        // 모든 메뉴 중 , 사용자가 일주일 내 주문하지 않은 메뉴 들 List로 반출
        // sql 튜닝 -> order_entity , idx_order_date
        List<MenuEntity> allMenu = new ArrayList<>();
        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            List<OrderDetailEntity> list = new ArrayList<>();
            // 일주일 내 데이터 뽑아오기
            Boolean isCheck = getListWeek(member, list);
            // 모든 메뉴 뽑아오기
            allMenu = menuRepository.findAll();
            // 비교 후 동일한 메뉴이름은 제거
            deleteDuplicatedMenu(allMenu, list);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRecommendationResponseDto.success(allMenu);
    }

    private static void deleteDuplicatedMenu(List<MenuEntity> allMenu, List<OrderDetailEntity> list) {
        allMenu.removeIf(menu ->
                list.stream().anyMatch(orderDetail -> menu.getMenuName().equals(orderDetail.getMenuName()))
        );
    }

    private Boolean getListWeek(MemberEntity member, List<OrderDetailEntity> list) {
        for (OrderEntity orderEntity : orderRepository.findByMenuBeforeWeek(member.getMemberId())) {
            if (orderEntity == null){
                return false;
            }
            for (OrderDetailEntity orderDetailEntity : orderDetailRepository.findByOrderOrderId(orderEntity.getOrderId())) {
                list.add(orderDetailEntity);
            }
        }
        return true;
    }
}
