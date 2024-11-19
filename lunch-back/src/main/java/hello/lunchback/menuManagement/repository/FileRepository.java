package hello.lunchback.menuManagement.repository;

import hello.lunchback.menuManagement.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity,String> {
}
