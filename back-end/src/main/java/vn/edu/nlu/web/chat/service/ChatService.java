package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.chat.request.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.chat.request.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.chat.response.ChatCreateResponse;
import vn.edu.nlu.web.chat.dto.chat.response.ChatDetailsResponse;

public interface ChatService {

    ChatCreateResponse create(ChatCreateRequest request);

    ChatDetailsResponse getChatDetailsById(Long id);

    void update(Long id, ChatUpdateRequest request);

    void delete(Long id);

    boolean exist(Long id);


}
