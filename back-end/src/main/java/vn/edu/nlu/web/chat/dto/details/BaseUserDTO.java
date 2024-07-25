package vn.edu.nlu.web.chat.dto.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseUserDTO {

    @JsonProperty(value = "uid")
    private Long id;

    private String profileImage;

}
