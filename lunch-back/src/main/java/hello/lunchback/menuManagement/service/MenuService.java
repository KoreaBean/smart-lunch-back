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


    ResponseEntity<? super PostMenuAddResponseDto> add(PostMenuAddRequestDto dto);

    ResponseEntity<? super GetStoreMenuListResponseDto> menuList(Integer storeId) throws MalformedURLException;

    String getImage(String fileName);

    ResponseEntity<? super PutStoreMenuDelete> delete(Integer storeId, Integer menuId);

    ResponseEntity<? super PostMenuUpdateResponseDto> menuUpdate(Integer storeId, Integer menuId, PostMenuUpdateRequestDto dto);
}
