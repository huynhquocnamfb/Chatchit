package vn.edu.nlu.web.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import vn.edu.nlu.web.chat.enums.ContactStatus;

@Data
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity {
    @Column(name = "email1",nullable = false)
    private String email1;

    @Column(name = "email2", nullable = false)
    private String email2;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactStatus status;

}
