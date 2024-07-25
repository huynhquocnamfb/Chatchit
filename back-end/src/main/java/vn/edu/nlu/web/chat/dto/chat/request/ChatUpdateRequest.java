package vn.edu.nlu.web.chat.dto.chat.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChatUpdateRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

}
