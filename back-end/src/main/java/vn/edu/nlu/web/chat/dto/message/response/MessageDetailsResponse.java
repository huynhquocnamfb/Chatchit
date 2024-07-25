package vn.edu.nlu.web.chat.dto.message.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.enums.MessageStatus;

import java.util.Date;


@Getter
@Setter
public class MessageDetailsResponse extends EntityResponse {

    @JsonProperty("text")
    private String content;
    @JsonIgnore
    private MessageStatus messageStatus;
    private Meta meta = new Meta();

//    @JsonFormat(pattern = DATE_TIME_FORMAT2)
    public Date getTime() {
        return super.getCreatedAt();
    }

    @JsonProperty(value = "mId")
    @Override
    public Long getId() {
        return super.getId();
    }

    public Meta getMeta() {
        switch (messageStatus) {
            case FORWARDED:
                meta.setForwarded(true);
                break;
            case READ:
                meta.setRead(true);
            case DELIVERED:
                meta.setReceived(true);
            case SENT:
                meta.setSent(true);
                break;
            default:
                break;
        }
        return meta;
    }
    @Setter
    @Getter
    @NoArgsConstructor
   public final class Meta {
        boolean sent;
        boolean received;
        boolean read;
        @JsonProperty(value = "isForwarded")
        boolean isForwarded;
        long sender;
    }
}



