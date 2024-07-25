package vn.edu.nlu.web.chat.dto.theme.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.utils.DateUtils;

import java.util.Date;


@Getter
@Setter
public class ThemeDetailsResponse extends EntityResponse {

    private Long user_id;

    private Long color_id;

    private Long image_id;

}
