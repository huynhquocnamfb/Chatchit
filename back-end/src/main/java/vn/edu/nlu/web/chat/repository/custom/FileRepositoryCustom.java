package vn.edu.nlu.web.chat.repository.custom;

import org.springframework.data.domain.Pageable;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;

public interface FileRepositoryCustom {

    PageResponse<?> searchFiles(Pageable pageable, String fileName);
}
