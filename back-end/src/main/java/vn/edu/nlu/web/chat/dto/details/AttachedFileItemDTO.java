package vn.edu.nlu.web.chat.dto.details;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachedFileItemDTO {
    private Long id;
    private String fileName;
    private String size;
    private String downloadUrl;
    private String icon;
}
