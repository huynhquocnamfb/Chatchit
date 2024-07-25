package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.enums.ChatType;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.custom.ChatRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryCustom {
    List<Chat> findAllByType(ChatType type);

    Optional<Chat> findById(Long id);

}
