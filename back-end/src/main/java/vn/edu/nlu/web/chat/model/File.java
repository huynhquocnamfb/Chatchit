package vn.edu.nlu.web.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "files")
public class File extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "size")
    private long size;

    @Column(name = "extension")
    private String extension;

    @Column(name = "is_directory")
    private boolean isDirectory;

    @PostPersist
    public void setPath() {
        this.setPath(path + this.getId());
    }

}