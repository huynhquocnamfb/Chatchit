package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.chat.request.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.chat.request.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.chat.response.ChatCreateResponse;
import vn.edu.nlu.web.chat.dto.chat.response.ChatDetailsResponse;
import vn.edu.nlu.web.chat.enums.ChatRole;
import vn.edu.nlu.web.chat.enums.ChatType;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.ChatParticipant;
import vn.edu.nlu.web.chat.repository.ChatParticipantRepository;
import vn.edu.nlu.web.chat.repository.ChatRepository;
import vn.edu.nlu.web.chat.service.ChatService;
import vn.edu.nlu.web.chat.service.UserService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserService userService;

    @Override
    public ChatCreateResponse create(ChatCreateRequest request) {
        checkUsersExist(request.getParticipantIds());
        if (request.getChatType() == ChatType.ONE_ON_ONE && request.getParticipantIds().size() == 2) {
            List<Long> existingOneOnOneChatIds = chatRepository.findAllByType(ChatType.ONE_ON_ONE)
                    .stream()
                    .map(Chat::getId)
                    .toList();

            boolean chatFound = false;
            for (Long chatId : existingOneOnOneChatIds) {
                List<ChatParticipant> participants = chatParticipantRepository.findAllByChatId(chatId);
                Set<Long> participantIdsInChat = participants.stream()
                        .map(ChatParticipant::getUserId)
                        .collect(Collectors.toSet());

                if (participantIdsInChat.containsAll(request.getParticipantIds())) {
                    chatFound = true;
                    break;
                }
            }

            if (!chatFound) {
                Chat newChat = new Chat();
                newChat.setType(request.getChatType());
                chatRepository.save(newChat);

                // Create participants for the new chat
                for (Long participantId : request.getParticipantIds()) {
                    ChatParticipant participant = new ChatParticipant();
                    participant.setChatId(newChat.getId());
                    participant.setUserId(participantId);
                    participant.setRole(ChatRole.ADMIN);
                    participant.setNotificationsEnabled(false);
                    chatParticipantRepository.save(participant);
                }
                return DataUtils.copyProperties(newChat,ChatCreateResponse.class);
            }
        }
        return null;
}
        private void checkUsersExist (List < Long > participantIds) {
            participantIds.forEach(userService::exists);
        }

        @Override
        public ChatDetailsResponse getChatDetailsById (Long id){
            Chat storedChat = getChatById(id);
            return DataUtils.copyProperties(storedChat, ChatDetailsResponse.class);
        }

        @Override
        public void update (Long id, ChatUpdateRequest request){
            Chat storedChat = getChatById(id);
            storedChat.setName(request.getName());
            chatRepository.save(storedChat);
        }

        @Override
        public void delete (Long id){
            Chat storedChat = getChatById(id);
            storedChat.setEntityStatus(EntityStatus.DELETED);
            chatRepository.save(storedChat);
        }

        @Override
        public boolean exist (Long id){
            return chatRepository.findByIdAndEntityStatusNotDeleted(id).isPresent();
        }

        private Chat getChatById (Long id){
            return chatRepository.findByIdAndEntityStatusNotDeleted(id).orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.not-exist")));
        }
    }
