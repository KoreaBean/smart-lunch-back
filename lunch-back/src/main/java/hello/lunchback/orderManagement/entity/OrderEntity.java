package hello.lunchback.orderManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.orderManagement.dto.OrderStatus;
import hello.lunchback.orderManagement.dto.request.MenuListItem;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.storeManagement.entity.StoreEntity;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "order_entity")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private MemberEntity member;
    @ManyToOne
    @ToString.Exclude
    private StoreEntity store;
    @OneToMany(mappedBy = "order", orphanRemoval = true,cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderDetailEntity> orderDetail = new ArrayList<>();
    private String orderDate;
    private boolean isPay = false;
    // 주문 상태
    private OrderStatus status = OrderStatus.ready;

    public OrderEntity(StoreEntity store, PostOrderRequestDto dto,MemberEntity member) {
        this.store = store;
        for (MenuListItem menuListItem : dto.getList()) {
            orderDetail.add(new OrderDetailEntity(menuListItem,this));
        }
        this.member = member;
        this.orderDate =createDate();
    }


    public String createDate(){
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }
}
