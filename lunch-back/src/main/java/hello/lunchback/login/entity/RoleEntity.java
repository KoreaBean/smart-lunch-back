package hello.lunchback.login.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity(name = "roles")
@Getter
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
    private String roleDescription;
    @ManyToMany(mappedBy = "roles")
    private List<MemberEntity> members;


    public void addMember(MemberEntity member){
        members.add(member);
    }
}
