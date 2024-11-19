package hello.lunchback.menuManagement.repository;

import hello.lunchback.menuManagement.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity,Integer> {

}
