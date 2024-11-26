package hello.lunchback.diet.service;

import hello.lunchback.diet.dto.response.getDietList;

public interface DietService {
    getDietList getDiet(String email);
}
