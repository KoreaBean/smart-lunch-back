package hello.lunchback.orderManagement.entity;

import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.orderManagement.dto.request.MenuListItem;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "order_detail")
@Data
@NoArgsConstructor
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;
    @ManyToOne()
    @ToString.Exclude
    private OrderEntity order;
    private String menuName;
    private Integer menuPrice;
    private Integer quantity;



    public OrderDetailEntity(MenuListItem menuListItem, OrderEntity orderEntity) {
        this.menuName = menuListItem.getMenuName();
        this.menuPrice = menuListItem.getPrice();
        this.quantity = menuListItem.getQuantity();
        this.order = orderEntity;
        order.getOrderDetail().add(this);
    }
}
