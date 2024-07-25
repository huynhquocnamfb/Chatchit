package vn.edu.nlu.web.chat.dto.conversation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.nlu.web.chat.dto.message.response.MessageDetailsResponse;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class ConversationDetailsResponse implements Serializable {
    private static final long serialVersionUID = -9344834595341231L;
    private Long id;
    @JsonProperty(value = "isGroupConversation")
    private boolean isChannel;
    @JsonProperty(value = "typingUser")
    private boolean isTyping;
    private List<MessageDetailsResponse> messages;

}
