package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.storeManagement.entity.StoreEntity;
import lombok.Data;

@Data
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
