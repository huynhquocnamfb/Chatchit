package vn.edu.nlu.web.chat.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class ContactDTO {

    private Long id;
    private String firstName;
    private String lastName;
    @JsonProperty(value = "profileImage")
    private String avatarUrl;
    private String about;
    private String email;
    private String location;
    private String status;
    private List<ChannelDTO> channels;
    private MediaDTO media;
    private AttachedFilesDTO attachedFiles;

}

