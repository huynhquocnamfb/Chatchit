package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.conversation.response.ConversationDetailsResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageDetailsResponse;
import vn.edu.nlu.web.chat.service.ContactService;
import vn.edu.nlu.web.chat.service.ConversationService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConversationServiceImpl implements ConversationService {
    private final ContactService contactService;

    @Override
    public ConversationDetailsResponse getConversationByContactId(Long contactId) {
        var messages = contactService.getMessagesByContactId(contactId).getItems();
        return ConversationDetailsResponse.builder()
                .id(123456789L)
                .messages((List<MessageDetailsResponse>) messages)
                .isChannel(false)
                .isTyping(false)
                .build();
    }
}
