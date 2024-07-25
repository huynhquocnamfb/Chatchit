package vn.edu.nlu.web.chat.repository.custom;

public interface ChatParticipantRepositoryCustom {

    Long findChatIdByPairParticipantIds(Long userId1, Long userId2);
}
