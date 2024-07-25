package vn.edu.nlu.web.chat.dto.user.request;

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
public class UserUpdateRequest {

    @Size(max = 50, message = "App name must be at most 50 characters")
    private String appName;

    @NotNull(message = "First name is required")
    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number")
    private String phone;

    @DateTimeFormat(pattern = DateUtils.DATE_FORMAT_2)
    private Date dateOfBirth;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;


}
