package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.Message;
import vn.edu.nlu.web.chat.repository.custom.MessageRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
    @Query("SELECT m FROM Message m " +
            "WHERE m.chatId = :chatId AND m.createdBy != :createdBy AND m.messageStatus = 'DELIVERED'")
    List<Message> findMessagesByChatIdAndCreatedByNotAndStatusDelivered(@Param("chatId") Long chatId, @Param("createdBy") Long createdBy);

}
