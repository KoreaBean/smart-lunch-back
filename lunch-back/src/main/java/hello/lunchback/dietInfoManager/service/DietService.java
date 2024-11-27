package hello.lunchback.dietInfoManager.service;

import hello.lunchback.dietInfoManager.dto.response.getDietList;

public interface DietService {
    getDietList getDietInfo(String email);
}
