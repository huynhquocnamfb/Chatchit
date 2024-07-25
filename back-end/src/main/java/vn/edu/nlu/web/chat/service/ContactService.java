package vn.edu.nlu.web.chat.service;


import vn.edu.nlu.web.chat.dto.common.response.PageResponse;

import vn.edu.nlu.web.chat.dto.contact.request.ContactAddRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactUnRequest;
import vn.edu.nlu.web.chat.dto.contact.response.ContactAddResponse;

public interface ContactService {
    boolean exits(String emailUser, String emailContact);

    ContactAddResponse addContact(ContactAddRequest request);

    void unContact(ContactUnRequest request);

    PageResponse<?> list(int pageNo, int pageSize, String sortBy);

    PageResponse<?> search(int pageNo, int pageSize, String search, String sortBy);

    PageResponse<?> getMessagesByContactId(Long contactId);
}
