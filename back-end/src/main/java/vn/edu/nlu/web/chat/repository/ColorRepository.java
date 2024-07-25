package vn.edu.nlu.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.dto.color.response.ColorDeleteResponse;
import vn.edu.nlu.web.chat.dto.color.response.ColorDetailsResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.model.Color;
import vn.edu.nlu.web.chat.repository.custom.ColorRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long>, ColorRepositoryCustom {

    Optional<Color> findColorByValue(String value);
    List<Color> getAllByAndEntityStatusIsNot(EntityStatus entityStatus);

    Optional<Color> findColorById(Long id);


}
