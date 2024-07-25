package vn.edu.nlu.web.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "colors")
public class Color extends AbstractEntity {
    @Column(name = "value", unique = true, nullable = false)
    private String value;
}