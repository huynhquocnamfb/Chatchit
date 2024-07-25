package vn.edu.nlu.web.chat.dto.theme.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.edu.nlu.web.chat.dto.validator.annotations.GenderSubset;
import vn.edu.nlu.web.chat.enums.Gender;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;

import static vn.edu.nlu.web.chat.enums.Gender.*;

@Getter
public class ThemeUpdateRequest {
    private Long color_id;
    private Long image_id;
}
