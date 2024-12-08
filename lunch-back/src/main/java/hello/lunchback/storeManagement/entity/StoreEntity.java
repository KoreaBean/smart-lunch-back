package hello.lunchback.storeManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.storeManagement.dto.request.PostStoreCreateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "store")
@Getter
@Setter
@NoArgsConstructor
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    private String businessNumber;
    private String storeName;
    private String storeDescription;
    private String storeImage;
    @OneToOne
    @JsonIgnore
    private MemberEntity member;
    @OneToMany(mappedBy = "store",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<MenuEntity> menuList = new ArrayList<>();
    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderEntity> order = new ArrayList<>();

    public StoreEntity(PostStoreCreateRequestDto dto, String uuidFilename, MemberEntity member) {
        this.businessNumber = dto.getBusinessNumber();
        this.storeName = dto.getStoreName();
        this.storeDescription = dto.getStoreDescription();
        this.storeImage = uuidFilename;
        this.member = member;
    }
}
