package vn.edu.nlu.web.chat.dto.contact.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.dto.contact.ContactDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class ContactListReponse extends EntityResponse {
    private List<ContactDTO> contactDTO;
}
