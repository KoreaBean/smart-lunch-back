package hello.lunchback.menuManagement.service;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.response.PostMenuAddResponseDto;
import org.springframework.http.ResponseEntity;

public interface MenuService {

    ResponseEntity<? super PostMenuAddResponseDto> add(PostMenuAddRequestDto dto);
}
