package hello.lunchback.menuManagement.service.impl;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.menuManagement.dto.request.PutStoreMenuDelete;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuItem;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuAddResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuUpdateResponseDto;
import hello.lunchback.menuManagement.entity.FileEntity;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.menuManagement.repository.FileRepository;
import hello.lunchback.menuManagement.repository.MenuRepository;
import hello.lunchback.menuManagement.service.MenuService;
import hello.lunchback.storeManagement.entity.StoreEntity;
import hello.lunchback.storeManagement.repository.StoreRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j

public class MenuServiceImpl implements MenuService {

    private final StoreRepository storeRepository;
    private final FileRepository fileRepository;
    private final MenuRepository menuRepository;

    @Value("${file.filePath}")
    private String filePath;
    @Value("${file.fileUrl}")
    private String fileUrl;


    @Override
    public ResponseEntity<? super PostMenuAddResponseDto> add(PostMenuAddRequestDto dto) {
        log.info("MenuServiceImpl : add : start");
        MenuEntity menuEntity = new MenuEntity(dto);
        StoreEntity storeEntity = new StoreEntity();
        try {
            storeEntity = storeRepository.findByStoreId(dto.getStoreId())
                    .orElse(null);
            if (storeEntity == null){
                log.info("MenuServiceImpl : add : Error");
                return PostMenuAddResponseDto.notExistedStore("등록되지 않은 가게입니다.");
            }
            String uuidFileName = saveFile(dto);
            menuEntity.setStore(storeEntity);
            menuEntity.setMenuImage(uuidFileName);
            menuRepository.save(menuEntity);
        }catch (Exception e){
            e.printStackTrace();
            return PostMenuAddResponseDto.databaseError();
        }
        log.info("MenuServiceImpl : add : complete");
        return PostMenuAddResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetStoreMenuListResponseDto> menuList(Integer storeId) {
        List<GetStoreMenuItem> item = new ArrayList<>();
        try {
            // 1. 사진, 2. 메뉴 이름 ,메뉴 설명 , 가격
            StoreEntity storeEntity = storeRepository.findByStoreId(storeId)
                    .orElse(null);
            if (storeEntity == null){
                return GetStoreMenuListResponseDto.databaseError();
            }
            List<MenuEntity> menuList = storeEntity.getMenuList();
            item = changeImageName(menuList);
        }catch (Exception e){
            e.printStackTrace();
            return GetStoreMenuListResponseDto.databaseError();
        }
        return GetStoreMenuListResponseDto.success(item);
    }

    @Override
    public String getImage(String fileName) {
        return filePath+fileName;
    }

    @Override
    @Transactional
    public ResponseEntity<? super PutStoreMenuDelete> delete(Integer storeId, Integer menuId) {

        try {
            StoreEntity storeEntity = storeRepository.findByStoreId(storeId)
                    .orElse(null);
            MenuEntity menuEntity = storeEntity.getMenuList().stream()
                    .filter(menu -> menu.getMenuId().equals(menuId))
                    .findFirst()
                    .orElse(null);
            storeEntity.getMenuList().remove(menuEntity);
        }catch (Exception e){
            e.printStackTrace();
            return PutStoreMenuDelete.databaseError();
        }
        return PutStoreMenuDelete.success();
    }

    @Override
    @Transactional
    public ResponseEntity<? super PostMenuUpdateResponseDto> menuUpdate(Integer storeId, Integer menuId, PostMenuUpdateRequestDto dto) {

        try {
            StoreEntity storeEntity = storeRepository.findByStoreId(storeId)
                    .orElse(null);
            MenuEntity menuEntity = storeEntity.getMenuList().stream()
                    .filter(menu -> menu.getMenuId().equals(menuId))
                    .findFirst()
                    .orElse(null);
            menuEntity.update(dto);
        }catch (Exception e){
            e.printStackTrace();
            return PostMenuUpdateResponseDto.databaseError();
        }
        return PostMenuUpdateResponseDto.success();
    }

    private List<GetStoreMenuItem> changeImageName(List<MenuEntity> menuList) {
        List<GetStoreMenuItem> list = new ArrayList<>();

        try {
            for (MenuEntity menuEntity : menuList) {
                String urlImage = fileUrl + menuEntity.getMenuImage();
                GetStoreMenuItem getStoreMenuItem = new GetStoreMenuItem(menuEntity,urlImage);
                list.add(getStoreMenuItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  list;
    }

    private String saveFile(PostMenuAddRequestDto dto) {
        MultipartFile menuImage = dto.getMenuImage();
        String originalFilename = menuImage.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID().toString() + extension;
        String saveFile = filePath + uuidFileName;
        FileEntity fileEntity = new FileEntity(uuidFileName,originalFilename);
        fileRepository.save(fileEntity);
        try {
            menuImage.transferTo(new File(saveFile));
        }catch (Exception e){
            e.printStackTrace();
        }
        return uuidFileName;
    }

}
