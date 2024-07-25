package vn.edu.nlu.web.chat.dto.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.enums.SocialStatus;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;


@Getter
@Setter
public class UserDetailsResponse extends EntityResponse {

    private String appName;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
    @JsonProperty(value = "profileImage")
    private String avatarUrl;

    @JsonFormat(pattern = DateUtils.DATE_FORMAT_2)
    private Date dob;

    private String roles;

    @JsonProperty(value = "status")
    private SocialStatus socialStatus;

    public String getSocialStatus() {
        return socialStatus == SocialStatus.ONLINE ? "Active" : socialStatus == SocialStatus.OFFLINE ? "Away" : "Do not disturb";
    }

}
