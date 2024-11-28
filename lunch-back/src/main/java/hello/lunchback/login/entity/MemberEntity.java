package hello.lunchback.login.entity;

import hello.lunchback.common.config.BcyptEncoder;
import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PutMemberUpdateRequestDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.storeManagement.entity.StoreEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "member")
@Getter
@NoArgsConstructor
public class MemberEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String memberPassword;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "memberId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private List<RoleEntity> roles = new ArrayList<>();
    @OneToOne(mappedBy = "member",orphanRemoval = true,cascade = CascadeType.ALL)
    private StoreEntity store;

    // 주문접수
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderEntity> orderList = new ArrayList<>();

    public void createMember(PostJoinRequestDto dto, String encodePassword) {
        this.memberName = dto.getName();
        this.memberPhone = dto.getPhone();
        this.memberEmail = dto.getEmail();
        this.memberPassword = encodePassword;
    }

    public void addRole(RoleEntity role){
        this.roles.add(role);
        role.addMember(this);
    }

    public void update(PutMemberUpdateRequestDto dto) {
        this.memberName = dto.getMemberName();
        this.memberPhone = dto.getMemberPhone();
        this.memberPassword = dto.getPassword();
    }


}
