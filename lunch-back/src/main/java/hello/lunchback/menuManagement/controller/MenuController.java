package hello.lunchback.menuManagement.controller;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.response.PostMenuAddResponseDto;
import hello.lunchback.menuManagement.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/add")
    public ResponseEntity<? super PostMenuAddResponseDto> add(@ModelAttribute PostMenuAddRequestDto dto){
        ResponseEntity<? super PostMenuAddResponseDto> result = menuService.add(dto);
        return result;
    }
}
