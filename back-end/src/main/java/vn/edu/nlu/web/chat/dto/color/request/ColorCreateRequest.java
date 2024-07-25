package vn.edu.nlu.web.chat.dto.color.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.edu.nlu.web.chat.enums.ChatType;

import java.util.List;

@Getter
@Setter
public class ColorCreateRequest {
    @NotBlank(message = "Id must not be blank")
    private String value;

}
