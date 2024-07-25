package vn.edu.nlu.web.chat.repository.custom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;


public interface ContactRepositoryCustom {
    boolean  exits(String emailUser,String emailContact);
    PageResponse<?> list(String email,int pageNo, int pageSize, String sortBy);


    PageResponse<?> search(String email,int pageNo, int pageSize, String search, String sortBy);
}
