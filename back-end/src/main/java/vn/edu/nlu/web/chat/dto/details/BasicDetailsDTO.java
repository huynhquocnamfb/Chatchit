package vn.edu.nlu.web.chat.dto.details;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BasicDetailsDTO {

    private String firstName;
    private String lastName;
    private String title;
    private String description;
    private String email;
    private String location;
    private String avatar;
    private String coverImage;


}
