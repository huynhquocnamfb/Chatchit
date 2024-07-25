package vn.edu.nlu.web.chat.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AttachedFileDTO {

    private int id;
    private String fileName;
    private String size;
    private String downloadUrl;
    private String icon;


}
