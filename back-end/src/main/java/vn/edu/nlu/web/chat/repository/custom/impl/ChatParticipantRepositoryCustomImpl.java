package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.repository.custom.ChatParticipantRepositoryCustom;
import vn.edu.nlu.web.chat.utils.DummyData;

import java.util.List;

@Repository
public class ChatParticipantRepositoryCustomImpl implements ChatParticipantRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findChatIdByPairParticipantIds(Long userId1, Long userId2) {
        String jpql = "SELECT cp.chatId FROM ChatParticipant cp " +
                "WHERE cp.userId IN (:userId1, :userId2) " +
                "GROUP BY cp.chatId " +
                "HAVING COUNT(DISTINCT cp.userId) = 2";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class)
                .setParameter("userId1", userId1)
                .setParameter("userId2", userId2);

        List<Long> chatIds = query.getResultList();

        if (chatIds.isEmpty()) {
            return DummyData.NO_CHAT_ID; // No chat found with both participants
        }
        return chatIds.get(0);
    }
}
