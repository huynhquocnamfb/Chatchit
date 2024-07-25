package vn.edu.nlu.web.chat.repository.custom.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.repository.custom.FileRepositoryCustom;

@Repository
public class FileRepositoryCustomImpl implements FileRepositoryCustom {

    @Override
    public PageResponse<?> searchFiles(Pageable pageable, String fileName) {
        return null;
    }
}
