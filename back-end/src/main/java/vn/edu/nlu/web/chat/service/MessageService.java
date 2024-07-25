package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.message.request.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.message.request.MessageUpdateRequest;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageCreateResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageDetailsResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageUpdateResponse;
import vn.edu.nlu.web.chat.exception.ApiRequestException;

public interface MessageService {

    MessageCreateResponse create(Long chatId, MessageCreateRequest request);

    MessageUpdateResponse update(Long id, MessageUpdateRequest request);

    void delete(Long id);

    MessageDetailsResponse getDetails(Long id);

    PageResponse<?> searchMessagesWithPaginationAndSorting(long chatId, int pageNo, int pageSize, String query, String sortBy);

    PageResponse<?> search(long chatId);

    void seen(Long chatId) throws Exception;
}
