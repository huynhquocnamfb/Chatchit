package vn.edu.nlu.web.chat.dto.user.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

//    @NotEmpty(message = "Name cannot be empty")
//    private String firstName;
//
//    private String lastName;
//
//    @NotEmpty(message = "Phone number cannot be empty")
//    @Pattern(regexp = "^[0-9]{8,11}+$", message = "Phone number must contain only digits")
//    private String phone;
//
//
//    private Date dob;
//
//    @NotNull(message = "type must be not null")
//    @GenderSubset(anyOf = {MALE,FEMALE,OTHER})
//    private Gender gender;

}
