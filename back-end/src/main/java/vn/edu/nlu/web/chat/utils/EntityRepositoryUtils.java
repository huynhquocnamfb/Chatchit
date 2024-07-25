package vn.edu.nlu.web.chat.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.edu.nlu.web.chat.enums.EntityStatus;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class EntityRepositoryUtils {

    private final EntityManager entityManager;

    public <T> Optional<T> findByIdAndEntityStatusNotDeleted(Class<T> entityClass, Long id) {
        log.info("Finding {} by ID={} and entityStatus not deleted", entityClass.getSimpleName(), id);
        String jpqlQuery = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id AND e.entityStatus != :deletedStatus";
        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("id", id)
                .setParameter("deletedStatus", EntityStatus.DELETED);

        Optional<T> entityOptional = query.getResultList().stream().findFirst();
        if (entityOptional.isPresent()) {
            log.info("{} found with ID={}, entityStatus not deleted", entityClass.getSimpleName(), id);
        } else {
            log.error("{} not found with ID={} or entityStatus is deleted", entityClass.getSimpleName(), id);
        }

        return entityOptional;
    }
}