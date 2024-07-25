package vn.edu.nlu.web.chat.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.nlu.web.chat.enums.TokenType;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token extends AbstractEntity {

    @Column(name = "expired_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;

    @Column(name = "value")
    private String value;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "user_id")
    private Long userId;

}