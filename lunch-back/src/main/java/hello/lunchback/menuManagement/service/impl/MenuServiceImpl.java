package hello.lunchback.menuManagement.service.impl;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.response.PostMenuAddResponseDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
