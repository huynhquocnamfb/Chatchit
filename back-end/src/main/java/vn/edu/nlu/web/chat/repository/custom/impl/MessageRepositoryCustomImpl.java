package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.message.response.MessageDetailsResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.Message;
import vn.edu.nlu.web.chat.repository.custom.MessageRepositoryCustom;
import vn.edu.nlu.web.chat.service.AuthenticationService;
import vn.edu.nlu.web.chat.utils.DataUtils;
import vn.edu.nlu.web.chat.utils.EntityRepositoryUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static vn.edu.nlu.web.chat.utils.AppConst.SORT_BY;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;
    private static final String LIKE_FORMAT = "%%%s%%";
    private final AuthenticationService authenticationService;

    @Override
    public Optional<Message> findByIdAndEntityStatusNotDeleted(Long id) {
        EntityRepositoryUtils entityRepositoryUtils = new EntityRepositoryUtils(entityManager);
        return entityRepositoryUtils.findByIdAndEntityStatusNotDeleted(Message.class, id);
    }

    @Override
    public PageResponse<?> searchMessagesWithPaginationAndSorting(long chatId, int pageNo, int pageSize, String search, String sortBy) {
        try {
            log.info("Executing search messages for chatId={} with keyword={}", chatId, search);
            Long userId = authenticationService.getCurrentUserId();
            StringBuilder sqlQuery = new StringBuilder("SELECT m FROM Message m WHERE m.chatId = :chatId AND  m.deleteById != " +userId +" AND  m.deleteById != -2"); // -2 is value when both deleted
            if (StringUtils.hasLength(search)) {
                sqlQuery.append(" AND LOWER(m.content) LIKE LOWER(:content)");
            }

            if (StringUtils.hasLength(sortBy)) {
                // Implement sorting logic similar to the searchUsersWithPaginationAndSorting method
                Pattern pattern = Pattern.compile(SORT_BY);
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()) {
                    sqlQuery.append(String.format(" ORDER BY m.%s %s", matcher.group(1), matcher.group(3)));
                }
            }
            // Get list of messages
            Query selectQuery = entityManager.createQuery(sqlQuery.toString());
            selectQuery.setParameter("chatId", chatId);
            if (StringUtils.hasLength(search)) {
                selectQuery.setParameter("content", String.format(LIKE_FORMAT, search));
            }
            selectQuery.setFirstResult(pageNo * pageSize);
            selectQuery.setMaxResults(pageSize);
            List<Message> messages = selectQuery.getResultList();

            // Count messages
            StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM Message m WHERE m.chatId = :chatId");
            if (StringUtils.hasLength(search)) {
                sqlCountQuery.append(" AND LOWER(m.content) LIKE LOWER(:content)");
            }

            Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
            countQuery.setParameter("chatId", chatId);
            if (StringUtils.hasLength(search)) {
                countQuery.setParameter("content", String.format(LIKE_FORMAT, search));
            }
            Long totalElements = (Long) countQuery.getSingleResult();

            log.info("Total messages found: {}", totalElements);

            // Create page response
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Message> page = new PageImpl<>(messages, pageable, totalElements);

            List<MessageDetailsResponse> dtoMessages = messages.stream().map(m -> {
                var result = DataUtils.copyProperties(m, MessageDetailsResponse.class);
                result.getMeta().setSender(m.getSenderId());
                return result;
            }).toList();
            
            return PageResponse.builder()
                    .page(pageNo)
                    .size(pageSize)
                    .total(page.getTotalPages())
                    .items(dtoMessages)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while searching messages: {}", e.getMessage());
            throw new ApiRequestException(Translator.toLocale("error.invalid.param"));
        }
    }
}
