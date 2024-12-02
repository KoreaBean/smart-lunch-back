package hello.lunchback.storeManagement.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetStoreListResponseDto {

    private  final List<MenuInfoItem> list = new ArrayList<>();

    public GetStoreListResponseDto(List<MenuInfoItem> list) {
        this.list.addAll(list);
    }


    public static GetStoreListResponseDto success(List<MenuInfoItem> list) {
        GetStoreListResponseDto result = new GetStoreListResponseDto(list);
        return result;
    }
}