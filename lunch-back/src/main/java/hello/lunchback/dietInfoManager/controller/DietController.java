package hello.lunchback.diet.controller;

import hello.lunchback.diet.dto.response.getDietList;
import hello.lunchback.diet.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DietController {

    private final DietService dietService;

    @GetMapping(value = "/diet",produces = MediaType.APPLICATION_JSON_VALUE)
    public getDietList diet(@AuthenticationPrincipal String email){
        getDietList diet = dietService.getDiet(email);
        return diet;
    }



}
