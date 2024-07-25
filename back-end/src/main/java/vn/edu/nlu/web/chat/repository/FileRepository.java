package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.model.File;
import vn.edu.nlu.web.chat.repository.custom.FileRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {

    Optional<File> findByIdAndEntityStatusIsNot(Long id, EntityStatus status);


}
