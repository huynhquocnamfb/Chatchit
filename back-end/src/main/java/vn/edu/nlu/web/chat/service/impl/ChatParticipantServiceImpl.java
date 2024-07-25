package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.model.ChatParticipant;
import vn.edu.nlu.web.chat.repository.ChatParticipantRepository;
import vn.edu.nlu.web.chat.repository.ChatRepository;
import vn.edu.nlu.web.chat.service.ChatParticipantService;


@RequiredArgsConstructor
@Service
public class ChatParticipantServiceImpl implements ChatParticipantService {
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRepository chatRepository;
    @Override
    public ChatParticipant createChatParticipant(Long chatId, Long userId) {
        return null;
    }

    @Override
    public Long getChatIdByPairUserId(Long userId1, Long userId2) {
        return chatParticipantRepository.findChatIdByPairParticipantIds(userId1,userId2);
    }

}
