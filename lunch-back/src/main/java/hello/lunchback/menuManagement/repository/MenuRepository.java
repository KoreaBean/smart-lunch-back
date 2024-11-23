package hello.lunchback.menuManagement.repository;

import hello.lunchback.menuManagement.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity,Integer> {


    MenuEntity findByMenuName(String name);

}
