package vn.edu.nlu.web.chat.dto.color.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorDetailsRequest {
    @NotBlank(message = "Id must not be blank")
    private Long colorId;
}
