package vn.edu.nlu.web.chat.repository.custom;

import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.model.Message;

import java.util.Optional;

public interface MessageRepositoryCustom {
    Optional<Message> findByIdAndEntityStatusNotDeleted(Long id);

    PageResponse<?> searchMessagesWithPaginationAndSorting(long chatId, int pageNo, int pageSize, String search, String sortBy);



}
