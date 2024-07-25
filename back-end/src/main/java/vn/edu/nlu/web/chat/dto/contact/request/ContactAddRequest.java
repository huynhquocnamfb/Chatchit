package vn.edu.nlu.web.chat.dto.contact.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactAddRequest {

    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "Invitation Message cannot be null")
    private String message;

}
