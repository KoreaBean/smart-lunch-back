package hello.lunchback.storeManagement.service.impl;

import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.storeManagement.dto.response.GetStoreListResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreResponseDto;
import hello.lunchback.storeManagement.dto.response.MenuInfoItem;
import hello.lunchback.storeManagement.dto.response.StoreItem;
import hello.lunchback.storeManagement.entity.StoreEntity;
import hello.lunchback.storeManagement.repository.StoreRepository;
import hello.lunchback.storeManagement.service.StoreService;
import hello.lunchback.waitManagement.WaitingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final WaitingManager waitingManager;

    @Override
    public GetStoreResponseDto storeList(String email) {
        List<StoreEntity> all = storeRepository.findAll();
        List<StoreItem> list = new ArrayList<>();
        String state = "";
        for (StoreEntity storeEntity : all) {
            Integer busy = waitingManager.busy(storeEntity.getStoreId());
            if (busy < 3){
                state = "쾌적";
            } else if (busy < 10){
                state = "보통";
            } else {
                state = "매우 혼잡";
            }
            StoreItem storeItem = new StoreItem(storeEntity,state);
            list.add(storeItem);
        }
        return GetStoreResponseDto.success(list);
    }

    @Override
    public GetStoreListResponseDto storeMenuList(Integer storeId) {
        StoreEntity storeEntity = storeRepository.findByStoreId(storeId)
                .orElse(null);
        List<MenuInfoItem> list = new ArrayList<>();
        for (MenuEntity menuEntity : storeEntity.getMenuList()) {
            MenuInfoItem menuInfoItem = new MenuInfoItem(menuEntity);
            list.add(menuInfoItem);
        }

        return GetStoreListResponseDto.success(list);
    }

}
