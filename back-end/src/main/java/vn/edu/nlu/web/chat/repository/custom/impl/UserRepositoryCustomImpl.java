package vn.edu.nlu.web.chat.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.mapper.UserDetailsMapper;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.custom.UserRepositoryCustom;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static vn.edu.nlu.web.chat.utils.AppConst.SORT_BY;

@Slf4j
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserDetailsMapper userDetailsMapper;

    private static final String LIKE_FORMAT = "%%%s%%";

    @Autowired
    public UserRepositoryCustomImpl(UserDetailsMapper userDetailsMapper) {
        this.userDetailsMapper = userDetailsMapper;
    }

    /**
     * Search user using keyword and
     *
     * @param pageNo
     * @param pageSize
     * @param search
     * @param sortBy
     * @return user list with sorting and paging
     */
    public PageResponse<?> searchUsersWithPaginationAndSorting(int pageNo, int pageSize, String search, String sortBy) {
        try {
            log.info("Execute search user with keyword={}", search);

            StringBuilder sqlQuery = new StringBuilder("SELECT u FROM User u WHERE 1=1");
            if (StringUtils.hasLength(search)) {
                sqlQuery.append(" AND LOWER(u.firstName) LIKE LOWER(:firstName)");
                sqlQuery.append(" OR LOWER(u.lastName) LIKE LOWER(:lastName)");
                sqlQuery.append(" OR LOWER(u.email) LIKE LOWER(:email)");
                sqlQuery.append(" OR LOWER(u.appName) LIKE LOWER(:appName)");
                sqlQuery.append(" OR LOWER(u.phone) LIKE LOWER(:phone)");
            }

            if (StringUtils.hasLength(sortBy)) {
                // firstName:asc|desc
                Pattern pattern = Pattern.compile(SORT_BY);
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()) {
                    sqlQuery.append(String.format(" ORDER BY u.%s %s", matcher.group(1), matcher.group(3)));
                }
            }

            // Get list of users
            Query selectQuery = entityManager.createQuery(sqlQuery.toString());
            if (StringUtils.hasLength(search)) {
                selectQuery.setParameter("firstName", String.format(LIKE_FORMAT, search));
                selectQuery.setParameter("lastName", String.format(LIKE_FORMAT, search));
                selectQuery.setParameter("email", String.format(LIKE_FORMAT, search));
                selectQuery.setParameter("appName", String.format(LIKE_FORMAT, search));
                selectQuery.setParameter("phone", String.format(LIKE_FORMAT, search));
            }
            selectQuery.setFirstResult(pageNo);
            selectQuery.setMaxResults(pageSize);
            List<?> usersDto = selectQuery.getResultList()
                    .stream()
                    .map(userDetailsMapper)
                    .toList();

            // Count users (Search ver2 with ? position)
            StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u WHERE 1=1");
            if (StringUtils.hasLength(search)) {
                sqlCountQuery.append(" AND LOWER(u.firstName) LIKE LOWER(?1)");
                sqlCountQuery.append(" OR LOWER(u.lastName) LIKE LOWER(?2)");
                sqlCountQuery.append(" OR LOWER(u.email) LIKE LOWER(?3)");
                sqlCountQuery.append(" OR LOWER(u.phone) LIKE LOWER(?4)");
                sqlCountQuery.append(" OR LOWER(u.appName) LIKE LOWER(?5)");
            }

            Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
            if (StringUtils.hasLength(search)) {
                countQuery.setParameter(1, String.format(LIKE_FORMAT, search));
                countQuery.setParameter(2, String.format(LIKE_FORMAT, search));
                countQuery.setParameter(3, String.format(LIKE_FORMAT, search));
                countQuery.setParameter(4, String.format(LIKE_FORMAT, search));
                countQuery.setParameter(5, String.format(LIKE_FORMAT, search));
                countQuery.getSingleResult();
            }

            Long totalElements = (Long) countQuery.getSingleResult();
            log.info("totalElements={}", totalElements);
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<?> page = new PageImpl<>(usersDto, pageable, totalElements);

            return PageResponse.builder()
                    .page(pageNo)
                    .size(pageSize)
                    .total(page.getTotalPages())
                    .items(usersDto)
                    .build();
        } catch (Exception e) {
            log.error("{0}", e);
        }
        throw new ApiRequestException(Translator.toLocale("error.invalid.param"));
    }

    @Override
    public Optional<User> findByIdAndEntityStatusNotDeleted(Long id) {
        log.info("Finding user by ID={} and entityStatus not deleted", id);
        String jpqlQuery = "SELECT u FROM User u WHERE u.id = :id AND u.entityStatus != :deletedStatus";
        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("id", id)
                .setParameter("deletedStatus", EntityStatus.DELETED);

        Optional<User> userOptional = query.getResultList().stream().findFirst();
        if (userOptional.isPresent()) {
            log.info("User found with ID={}, entityStatus not deleted", id);
        } else {
            log.error("User not found with ID={} or entityStatus is deleted", id);
        }

        return userOptional;
    }

}
