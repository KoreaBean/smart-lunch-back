package hello.lunchback.dietInfoManager.service.impl;

import hello.lunchback.dietInfoManager.dto.response.Diet;
import hello.lunchback.dietInfoManager.dto.response.MenuItem;
import hello.lunchback.dietInfoManager.dto.response.getDietList;
import hello.lunchback.dietInfoManager.service.DietService;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.menuManagement.repository.MenuRepository;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.orderManagement.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietServiceImpl implements DietService {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public getDietList getDietInfo(String email) {
        //해당일 구매한 이력 모두 가져와서 menuItem 반환
        getDietList getDietList = new getDietList();
        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            for (OrderEntity orderEntity : member.getOrderList()) {
                Diet diet = new Diet();
                diet.setDate(orderEntity.getOrderDate());
                List<OrderDetailEntity> byOrderId = orderDetailRepository.findByOrderOrderId(orderEntity.getOrderId());
                for (OrderDetailEntity orderDetailEntity : byOrderId) {
                    MenuEntity byMenuName = menuRepository.findByMenuName(orderDetailEntity.getMenuName());
                    MenuItem menuItem = new MenuItem(byMenuName);
                    diet.getMenuItems().add(menuItem);
                }
                getDietList.getDietList().add(diet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return getDietList;
    }


}
