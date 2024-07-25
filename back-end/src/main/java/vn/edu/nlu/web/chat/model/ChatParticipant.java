package vn.edu.nlu.web.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import vn.edu.nlu.web.chat.enums.ChatRole;

import java.util.Date;

@Data
@Entity
@Table(name = "chat_participants")
public class ChatParticipant extends AbstractEntity {

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "participant_id", nullable = false)
    private Long userId; // user id

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_role")
    private ChatRole role;

    @Column(name = "last_seen")
    private Date lastSeen;

    @Column(name = "notifications_enabled")
    private boolean notificationsEnabled;
}