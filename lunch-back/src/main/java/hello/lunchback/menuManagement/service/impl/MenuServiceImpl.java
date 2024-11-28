package hello.lunchback.menuManagement.service.impl;

import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.menuManagement.dto.request.PutStoreMenuDelete;
import hello.lunchback.menuManagement.dto.response.*;
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
import org.apache.coyote.Response;
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
    private final MemberRepository memberRepository;

    @Value("${file.filePath}")
    private String filePath;
    @Value("${file.fileUrl}")
    private String fileUrl;


    @Override
    public void createMenuData(PostMenuAddRequestDto dto, String email) {
        log.info("MenuServiceImpl : add : start");
        MenuEntity menuEntity = new MenuEntity(dto);
        StoreEntity storeEntity = new StoreEntity();
        try {

            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            storeEntity = member.getStore();

            String uuidFileName = saveFile(dto);
            menuEntity.setStore(storeEntity);
            menuEntity.setMenuImage(uuidFileName);
            menuRepository.save(menuEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("MenuServiceImpl : add : complete");
    }

    @Override
    public ResponseEntity<? super GetStoreMenuListResponseDto> getMenuList(String email) {
        List<MenuEntity> menuList = new ArrayList<>();
        // 본인 상점의 모든 메뉴를 반환할때 메뉴의 모든 데이타 반환
        try {
            menuList = memberRepository.findByMemberEmail(email)
                    .orElse(null)
                    .getStore()
                    .getMenuList();
            if (menuList == null){
                return GetStoreMenuListResponseDto.notExistedMenu();
            }
        }catch (Exception e){
            e.printStackTrace();
            return GetStoreMenuListResponseDto.databaseError();
        }
        return GetStoreMenuListResponseDto.success(menuList);
    }

    @Override
    public String getImage(String fileName) {
        return filePath+fileName;
    }

    @Override
    @Transactional
    public void delete(String email, Integer menuId) {

        // email로 멤버 찾고, store 찾아가서 , 메뉴Id로 해당 메뉴 삭제
        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            StoreEntity store = member.getStore();
            for (MenuEntity menu : store.getMenuList()) {
                if (menu.getMenuId().equals(menuId)){
                    store.getMenuList().remove(menu);
                    break;
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void menuUpdate(String email, Integer menuId, PostMenuUpdateRequestDto dto) {

        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            StoreEntity storeEntity = member.getStore();
            MenuEntity menuEntity = storeEntity.getMenuList().stream()
                    .filter(menu -> menu.getMenuId().equals(menuId))
                    .findFirst()
                    .orElse(null);
            menuEntity.update(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public GetStoreMenuDetailResponseDto getMenuInfo(String email, Integer menuId) {

        MemberEntity member = memberRepository.findByMemberEmail(email)
                .orElse(null);
        MenuEntity menuEntity = member.getStore().getMenuList().stream()
                .filter(menu -> menu.getMenuId().equals(menuId))
                .findFirst()
                .orElse(null);
        menuEntity.setMenuImage(changeImageName(menuEntity.getMenuImage()));
        return GetStoreMenuDetailResponseDto.success(menuEntity);
    }


    private String changeImageName(String menuImage) {
        String urlImage = "";
        try {
            urlImage = fileUrl + menuImage;
        }catch (Exception e){
            e.printStackTrace();
        }
        return urlImage;
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
        MultipartFile menuImage = dto.getMenuImg();
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
