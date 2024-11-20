package hello.lunchback.menuManagement.controller;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.menuManagement.dto.request.PutStoreMenuDelete;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuAddResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuUpdateResponseDto;
import hello.lunchback.menuManagement.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 메뉴 등록
    @PostMapping("/menu/add")
    public ResponseEntity<? super PostMenuAddResponseDto> add(@ModelAttribute PostMenuAddRequestDto dto){
        ResponseEntity<? super PostMenuAddResponseDto> result = menuService.add(dto);
        return result;
    }

    // 메뉴 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<? super GetStoreMenuListResponseDto> storeMenuList(@PathVariable(name = "storeId")Integer storeId) throws MalformedURLException {
        ResponseEntity<? super GetStoreMenuListResponseDto> result = menuService.menuList(storeId);
        return result;
    }

    // 이미지 조회
    @ResponseBody
    @GetMapping("/image/{fileName}")
    public Resource downloadImage(@PathVariable(name = "fileName")String fileName) throws MalformedURLException {
        return new UrlResource("file:"+menuService.getImage(fileName));
    }

    // 박승한 - 21학번
    // 메뉴 삭제
    @DeleteMapping("/{storeId}/{menuId}")
    public ResponseEntity<? super PutStoreMenuDelete> menuDelete(@PathVariable(name = "storeId")Integer storeId, @PathVariable(name = "menuId")Integer menuId){
        ResponseEntity<? super PutStoreMenuDelete> result = menuService.delete(storeId, menuId);
        return result;
    }

    // 품절 등록

    // 메뉴 수정
    @PutMapping("/{storeId}/{menuId}")
    public ResponseEntity<? super PostMenuUpdateResponseDto> menuUpdate(@PathVariable(name = "storeId")Integer storeId,
                                                                        @PathVariable(name = "menuId")Integer menuId,
                                                                        @RequestBody PostMenuUpdateRequestDto dto){
        ResponseEntity<? super PostMenuUpdateResponseDto> result = menuService.menuUpdate(storeId, menuId, dto);
        return result;
    }




}
