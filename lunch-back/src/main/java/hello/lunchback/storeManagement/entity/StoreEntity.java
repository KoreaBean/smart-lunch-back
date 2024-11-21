package hello.lunchback.storeManagement.entity;

import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "store")
@Data
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    private String businessNumber;
    private String storeName;
    private String storeDescription;
    private String storeImage;
    @OneToOne
    private MemberEntity member;
    @OneToMany(mappedBy = "store",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<MenuEntity> menuList = new ArrayList<>();
    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderEntity> order = new ArrayList<>();

}
