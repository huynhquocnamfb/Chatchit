package vn.edu.nlu.web.chat.dto.contact.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactUnRequest {
    @NotNull(message = "id cannot be null")
    private Long idContact;
}
