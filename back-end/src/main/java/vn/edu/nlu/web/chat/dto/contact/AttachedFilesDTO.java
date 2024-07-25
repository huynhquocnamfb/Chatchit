package vn.edu.nlu.web.chat.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor

@Data
public class AttachedFilesDTO {

    private int total;
    private List<AttachedFileDTO> list;


}
