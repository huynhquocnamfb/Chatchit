package vn.edu.nlu.web.chat.dto.color.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorUpdateRequest {

    @NotBlank(message = "value must not be blank")
    private String value;

}
