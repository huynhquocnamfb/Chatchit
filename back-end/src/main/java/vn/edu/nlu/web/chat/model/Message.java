package vn.edu.nlu.web.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import vn.edu.nlu.web.chat.enums.MessageStatus;

import java.util.Date;

@Data
@Entity
@Table(name = "messages")
public class Message extends AbstractEntity {
    public static final long NO_DELETE = -1;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "content", length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private MessageStatus messageStatus = MessageStatus.SENT;

    @Column(name = "delete_by_id")
    private Long deleteById = Message.NO_DELETE;
}