package vn.edu.nlu.web.chat.dto.chat.response;

import lombok.Getter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.enums.ChatType;

@Getter
public class ChatDetailsResponse extends EntityResponse {
    private ChatType type;

}
