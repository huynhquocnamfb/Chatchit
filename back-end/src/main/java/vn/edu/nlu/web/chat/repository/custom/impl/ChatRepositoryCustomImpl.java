package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.enums.ChatType;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.ChatDetails;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.ChatRepository;
import vn.edu.nlu.web.chat.repository.custom.ChatRepositoryCustom;

import java.util.List;
import java.util.Optional;
@Slf4j
@Repository
public class ChatRepositoryCustomImpl implements ChatRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Chat> findChatBySenderId(Long senderId, boolean createNewChatIfNotExists) {
        return Optional.empty();
    }

    @Override
    public void addUserToChatRoom(Long chatId, Long userId) {

    }

    @Override
    public Optional<ChatDetails> findChatDetailsByChatId(Long chatId) {
        return Optional.empty();
    }


    @Override
    public Optional<Chat> findByIdAndEntityStatusNotDeleted(Long id) {
        log.info("Finding chat by ID={} and entityStatus not deleted", id);
        String jpqlQuery = "SELECT c FROM Chat c WHERE c.id = :id AND c.entityStatus != :deletedStatus";
        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("id", id)
                .setParameter("deletedStatus", EntityStatus.DELETED);

        Optional<Chat> chatOptional = query.getResultList().stream().findFirst();
        if (chatOptional.isPresent()) {
            log.info("Chat found with ID={}, entityStatus not deleted", id);
        } else {
            log.error("Chat not found with ID={} or entityStatus is deleted", id);
        }

        return chatOptional;
    }

    @Override
    public List<Chat> findChatsByTypeAndParticipants(ChatType type, List<Long> participantIds) {
        return List.of();
    }
}
