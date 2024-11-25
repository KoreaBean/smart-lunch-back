package hello.lunchback.menuManagement.service;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.menuManagement.dto.request.PutStoreMenuDelete;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuDetailResponseDto;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuUpdateResponseDto;

import java.net.MalformedURLException;

public interface MenuService {


    void add(PostMenuAddRequestDto dto, String email);

    GetStoreMenuListResponseDto getMenuList(String email) throws MalformedURLException;

    String getImage(String fileName);

    PutStoreMenuDelete delete(String email, Integer menuId);


    PostMenuUpdateResponseDto menuUpdate(String email, Integer menuId, PostMenuUpdateRequestDto dto);

    GetStoreMenuDetailResponseDto getMenuInfo(String email, Integer menuId);
}
