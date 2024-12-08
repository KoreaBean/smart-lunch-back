package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.storeManagement.entity.StoreEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreItem {
    private Integer storeId;
    private String storeName;
    private String storeImage;
    private String state;


    public StoreItem(StoreEntity storeEntity, String state) {

        this.storeId = storeEntity.getStoreId();
        this.storeName = storeEntity.getStoreName();
        this.storeImage = storeEntity.getStoreImage();
        this.state = state;
    }
}
