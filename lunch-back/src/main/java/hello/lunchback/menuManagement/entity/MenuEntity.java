package hello.lunchback.menuManagement.entity;

import hello.lunchback.menuManagement.dto.request.PostMenuAddRequestDto;
import hello.lunchback.menuManagement.dto.request.PostMenuUpdateRequestDto;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.storeManagement.entity.StoreEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Interceptor;

@Entity(name = "menu")
@NoArgsConstructor
@Setter
@Getter
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;
    @ManyToOne
    private StoreEntity store;
    private String menuName;
    private String menuImage;
    @Column(columnDefinition = "TEXT")
    private String menuDescription;
    private Integer menuPrice;
    private String nutrients;

    public MenuEntity(PostMenuAddRequestDto dto) {
        this.menuName = dto.getMenuName();
        this.menuDescription = dto.getMenuDescription();
        this.menuPrice = dto.getMenuPrice();
    }


    public void setStore(StoreEntity storeEntity) {
        this.store = storeEntity;
    }

    public void update(PostMenuUpdateRequestDto dto) {
        this.menuName = dto.getMenuName();
        this.menuDescription = dto.getMenuDescription();
        this.menuPrice = dto.getMenuPrice();
        this.nutrients = dto.getNutrients();
    }
}
