package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.ChatParticipant;
import vn.edu.nlu.web.chat.repository.custom.ChatParticipantRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long>, ChatParticipantRepositoryCustom {

    List<ChatParticipant> findAllByChatId(Long chatId);
}