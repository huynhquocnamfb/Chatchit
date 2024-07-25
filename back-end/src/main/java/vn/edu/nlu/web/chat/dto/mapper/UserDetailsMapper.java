package vn.edu.nlu.web.chat.dto.mapper;

import vn.edu.nlu.web.chat.dto.user.response.UserDetailsResponse;
import vn.edu.nlu.web.chat.model.User;

import java.util.function.Function;

public interface UserDetailsMapper extends Function<User, UserDetailsResponse> {

}
