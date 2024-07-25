package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.dto.mapper.ContactDTOMapper;
import vn.edu.nlu.web.chat.dto.contact.ContactDTO;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.mapper.UserDetailsMapper;
import vn.edu.nlu.web.chat.dto.user.response.UserDetailsResponse;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.Contact;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.custom.ContactRepositoryCustom;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ContactRepositoryCustomImpl implements ContactRepositoryCustom {

    private static final String LIKE_FORMAT = "%%%s%%";

    @PersistenceContext
    private EntityManager entityManager;

    private final UserDetailsMapper userDetailsMapper;
    private final ContactDTOMapper contactDTOMapper;

    @Autowired
    public ContactRepositoryCustomImpl( UserDetailsMapper userDetailsMapper, ContactDTOMapper contactDTOMapper) {
        this.userDetailsMapper = userDetailsMapper;
        this.contactDTOMapper = contactDTOMapper;
    }


    @Override
    public boolean exits(String emailUser, String emailContact) {
        try {
            log.info("Check if contact exists={}", emailContact);
            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào (không phân biệt chữ hoa chữ thường)
            String contactJpql = "SELECT c FROM Contact c WHERE LOWER(c.email1) = LOWER(:email) OR LOWER(c.email2) = LOWER(:email)";
            TypedQuery<Contact> contactQuery = entityManager.createQuery(contactJpql, Contact.class);
            contactQuery.setParameter("email", emailUser.toLowerCase());
            List<Contact> contacts = contactQuery.getResultList();
            System.out.println(contacts.toString());
            List<String> contactEmails = new ArrayList<>();
            for (Contact contact : contacts) {
                // Lấy email khác mà không trùng với email truyền vào
                if (emailUser.equalsIgnoreCase(contact.getEmail1())) {
                    contactEmails.add(contact.getEmail2());
                } else {
                    System.out.println("mến ăn lồn");
                    contactEmails.add(contact.getEmail1());
                }
            }
            System.out.println(contactEmails.toString());
            for (String email : contactEmails) {
                if (email.equalsIgnoreCase(emailContact)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while listing contacts: {}", e.getMessage());
            throw new ApiRequestException("Error occurred while listing contacts", e);
        }
        return false;
    }

    @Override
    public PageResponse<?> list(String email, int pageNo, int pageSize, String sortBy) {
        try {
            log.info("Executing list contacts for email={}", email);

            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào (không phân biệt chữ hoa chữ thường)
            String contactJpql = "SELECT c FROM Contact c WHERE LOWER(c.email1) = LOWER(:email) OR LOWER(c.email2) = LOWER(:email)";
            TypedQuery<Contact> contactQuery = entityManager.createQuery(contactJpql, Contact.class);
            contactQuery.setParameter("email", email.toLowerCase());
            List<Contact> contacts = contactQuery.getResultList();

            List<String> contactEmails = new ArrayList<>();
            for (Contact contact : contacts) {
                // Lấy email khác mà không trùng với email truyền vào
                if (contact.getEmail1().equals(email)) {
                    contactEmails.add(contact.getEmail2());
                } else {
                    contactEmails.add(contact.getEmail1());
                }
            }

            String userJpql = "SELECT u FROM User u WHERE LOWER(u.email) IN (:emails)";
            TypedQuery<User> userQuery = entityManager.createQuery(userJpql, User.class);
            List<String> lowerCaseEmails = contactEmails.stream().map(String::toLowerCase).collect(Collectors.toList());
            userQuery.setParameter("emails", lowerCaseEmails);
            List<User> users = userQuery.getResultList();
            // Phân trang kết quả
            int start = pageNo * pageSize;
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            var contentDto = users.stream()
                    .map(contactDTOMapper)
                    .collect(Collectors.toList());
//            var contentDto = users.subList(start, end).stream().map(user -> DataUtils.copyProperties(user, ContactDTO.class)).toList();
            Page<ContactDTO> page = new PageImpl<>(contentDto, pageable, users.size());

            return PageResponse.builder()
                    .page(pageNo)
                    .size(pageSize)
                    .total(users.size())
                    .items(page.getContent())
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while listing contacts: {}", e.getMessage());
            throw new ApiRequestException("Error occurred while listing contacts", e);
        }
    }


    @Override
    public PageResponse<?> search(String email, int pageNo, int pageSize, String search, String sortBy) {
        try {
            log.info("Executing list contacts for email={}", email);

            // Tìm kiếm các contact có email1 hoặc email2 bằng email truyền vào (không phân biệt chữ hoa chữ thường)
            String contactJpql = "SELECT c FROM Contact c WHERE LOWER(c.email1) = LOWER(:email) OR LOWER(c.email2) = LOWER(:email)";
            TypedQuery<Contact> contactQuery = entityManager.createQuery(contactJpql, Contact.class);
            contactQuery.setParameter("email", email.toLowerCase());
            List<Contact> contacts = contactQuery.getResultList();

            List<String> contactEmails = new ArrayList<>();
            for (Contact contact : contacts) {
                // Lấy email khác mà không trùng với email truyền vào
                if (contact.getEmail1().equals(email)) {
                    contactEmails.add(contact.getEmail2());
                } else {
                    contactEmails.add(contact.getEmail1());
                }
            }

            // Tìm kiếm thông tin của các user contact dựa trên các email thu được
            String userJpql = "SELECT u FROM User u WHERE LOWER(u.email) IN (:emails)";
            TypedQuery<User> userQuery = entityManager.createQuery(userJpql, User.class);
            List<String> lowerCaseEmails = contactEmails.stream().map(String::toLowerCase).collect(Collectors.toList());
            userQuery.setParameter("emails", lowerCaseEmails);
            List<User> users = userQuery.getResultList();

            List<User> filteredUsers = new ArrayList<>();
            for (User user : users) {
                if ((user.getAppName() != null && user.getAppName().contains(search)) ||
                        (user.getEmail() != null && user.getEmail().contains(search)) ||
                        (user.getFirstName() != null && user.getFirstName().contains(search)) ||
                        (user.getLastName() != null && user.getLastName().contains(search))) {
                    filteredUsers.add(user);
                }
            }

            // Phân trang kết quả
            int start = pageNo * pageSize;
            int end = Math.min((start + pageSize), filteredUsers.size());
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            // var contentDto = filteredUsers.subList(start, end).stream().map(user -> DataUtils.copyProperties(user, UserDetailsResponse.class)).toList();
            var contentDto = users.stream()
                    .map(user -> contactDTOMapper.apply(user))
                    .collect(Collectors.toList());
            Page<ContactDTO> page = new PageImpl<>(contentDto, pageable, filteredUsers.size());

            return PageResponse.builder()
                    .page(pageNo)
                    .size(pageSize)
                    .total(page.getTotalPages())
                    .items(page.getContent())
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while listing contacts: {}", e.getMessage());
            throw new ApiRequestException("Error occurred while listing contacts", e);
        }
    }

    public static void main(String[] args) {
//        ContactRepositoryCustom contactRepositoryCustom;

    }
}
