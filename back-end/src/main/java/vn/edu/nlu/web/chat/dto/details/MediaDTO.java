package vn.edu.nlu.web.chat.dto.details;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MediaDTO {
    private int total;
    private List<MediaItemDTO> list;
}
