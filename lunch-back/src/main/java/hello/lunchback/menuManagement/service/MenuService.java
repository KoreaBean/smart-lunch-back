package hello.lunchback.menuManagement.service;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.menuManagement.dto.request.PutStoreMenuDelete;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuDetailResponseDto;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuUpdateResponseDto;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;

public interface MenuService {


    void createMenuData(PostMenuAddRequestDto dto, String email);

    ResponseEntity<? super GetStoreMenuListResponseDto> getMenuList(String email) throws MalformedURLException;

    String getImage(String fileName);

    void delete(String email, Integer menuId);


    void menuUpdate(String email, Integer menuId, PostMenuUpdateRequestDto dto);

    GetStoreMenuDetailResponseDto getMenuInfo(String email, Integer menuId);
}
