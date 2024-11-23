package hello.lunchback.menuManagement.service;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.menuManagement.dto.request.PutStoreMenuDelete;
import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuAddResponseDto;
import hello.lunchback.menuManagement.dto.response.PostMenuUpdateResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;

public interface MenuService {


    PostMenuAddResponseDto add(PostMenuAddRequestDto dto, String email);

    GetStoreMenuListResponseDto menuList(String email) throws MalformedURLException;

    String getImage(String fileName);

    PutStoreMenuDelete delete(String email, Integer menuId);

    PostMenuUpdateResponseDto menuUpdate(String email, Integer menuId, PostMenuUpdateRequestDto dto);
}
