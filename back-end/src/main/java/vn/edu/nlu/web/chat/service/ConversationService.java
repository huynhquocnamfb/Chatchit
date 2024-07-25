package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.conversation.response.ConversationDetailsResponse;

public interface ConversationService {

    ConversationDetailsResponse getConversationByContactId(Long contactId);
}
