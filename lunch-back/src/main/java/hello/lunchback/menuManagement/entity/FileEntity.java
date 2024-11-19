package hello.lunchback.menuManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FileEntity {

    @Id
    private String uuidFileName;
    private String originalFileName;

    public FileEntity(String uuidFileName, String originalFilename) {
        this.uuidFileName = uuidFileName;
        this.originalFileName = originalFilename;
    }

}
