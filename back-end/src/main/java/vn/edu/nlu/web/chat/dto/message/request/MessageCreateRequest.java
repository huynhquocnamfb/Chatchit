package vn.edu.nlu.web.chat.dto.message.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.edu.nlu.web.chat.dto.details.AttachedFilesDTO;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;

@Getter
@Setter
public class MessageCreateRequest {

    @NotEmpty(message = "Message content cannot be empty")
    @JsonAlias(value = {"text"})
    private String content;

    private AttachedFilesDTO attachments;
}
