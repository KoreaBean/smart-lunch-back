package hello.lunchback.menuManagement.repository;

import hello.lunchback.menuManagement.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,String> {

    FileEntity findByUuidFileName(String name);
}
