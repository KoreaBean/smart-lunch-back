package hello.lunchback.storeManagement.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetStoreResponseDto {

    private final List<StoreItem> list = new ArrayList<>();



    public GetStoreResponseDto(List<StoreItem> itemList) {
        this.list.addAll(itemList);
    }


    public static GetStoreResponseDto success(List<StoreItem> list) {
        GetStoreResponseDto result = new GetStoreResponseDto(list);
        return result;
    }


}
