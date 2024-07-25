package vn.edu.nlu.web.chat.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MediaDTO {

    private int total;
    private List<MediaItemDTO> list;


}
