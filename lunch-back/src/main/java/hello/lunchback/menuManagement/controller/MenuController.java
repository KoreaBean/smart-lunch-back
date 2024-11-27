package hello.lunchback.menuManagement.controller;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.menuManagement.dto.request.PutStoreMenuDelete;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuDetailResponseDto;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuUpdateResponseDto;
import hello.lunchback.menuManagement.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 메뉴 등록
    @PostMapping(value = "/menu/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@ModelAttribute PostMenuAddRequestDto dto, @AuthenticationPrincipal String email){
        menuService.createMenuData(dto, email);
    }

    // 메뉴 조회
    @GetMapping(value = "/store/list" , produces = MediaType.APPLICATION_JSON_VALUE)
    public GetStoreMenuListResponseDto storeMenuList(@AuthenticationPrincipal String email) throws MalformedURLException {
        GetStoreMenuListResponseDto result = menuService.getMenuList(email);
        return result;
    }

    // 메뉴 상세 조회
    @GetMapping("/store/list/{menuId}")
    public GetStoreMenuDetailResponseDto storeMenuListDetail(@AuthenticationPrincipal String email,
                                                             @PathVariable(name = "menuId") Integer menuId){
        GetStoreMenuDetailResponseDto result = menuService.getMenuInfo(email, menuId);
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
    @DeleteMapping(value = "/menu/delete/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void menuDelete(@PathVariable(name = "menuId")Integer menuId, @AuthenticationPrincipal String email){
        menuService.delete(email, menuId);
    }

    // 품절 등록

    // 메뉴 수정
    @PutMapping(value = "/menu/update/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void menuUpdate(@AuthenticationPrincipal String email,
                                                                        @PathVariable(name = "menuId")Integer menuId,
                                                                        @ModelAttribute PostMenuUpdateRequestDto dto){
        menuService.menuUpdate(email, menuId, dto);
    }




}
