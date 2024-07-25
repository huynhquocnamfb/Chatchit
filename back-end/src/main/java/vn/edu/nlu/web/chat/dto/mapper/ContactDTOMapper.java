package vn.edu.nlu.web.chat.dto.mapper;


import vn.edu.nlu.web.chat.dto.contact.ContactDTO;
import vn.edu.nlu.web.chat.model.User;

import java.util.function.Function;


public interface ContactDTOMapper extends Function<User, ContactDTO> {

}
