package vn.edu.nlu.web.chat.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class ErrorResponse implements Serializable {

    @JsonFormat(pattern = DateUtils.DATE_TIME_FORMAT2)
    private Date timestamp;
    private int status;
    private String path;
    private String error;
    private String message;

}