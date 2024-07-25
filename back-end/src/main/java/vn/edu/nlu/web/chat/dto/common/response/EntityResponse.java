package vn.edu.nlu.web.chat.dto.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import vn.edu.nlu.web.chat.enums.EntityStatus;

import java.io.Serializable;
import java.util.Date;

import static vn.edu.nlu.web.chat.utils.DateUtils.DATE_TIME_FORMAT2;

@Getter
@Setter
public abstract class EntityResponse implements Serializable {

    protected Long id;

    protected EntityStatus entityStatus;

    @JsonFormat(pattern = DATE_TIME_FORMAT2)
    protected Date createdAt;

    @JsonFormat(pattern = DATE_TIME_FORMAT2)
    protected Date updatedAt;

    protected Long createdBy;

    protected Long updatedBy;

    @JsonProperty(value = "entity_status")
    public String getEntityStatus() {
        return entityStatus.name();
    }
}
