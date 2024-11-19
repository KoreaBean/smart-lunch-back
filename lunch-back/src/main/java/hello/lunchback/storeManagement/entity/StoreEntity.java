package hello.lunchback.storeManagement.entity;

import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.menuManagement.entity.MenuEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "store")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    private String businessNumber;
    private String storeName;
    private String storeDescription;
    @OneToOne
    private MemberEntity member;
    @OneToMany(mappedBy = "store")
    private List<MenuEntity> menuList = new ArrayList<>();
}
