package vn.edu.nlu.web.chat.dto.mapper.impl;

import org.springframework.stereotype.Component;
import vn.edu.nlu.web.chat.dto.mapper.UserDetailsMapper;
import vn.edu.nlu.web.chat.dto.user.response.UserDetailsResponse;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.utils.DataUtils;


@Component
public class UserDetailsMapperImpl implements UserDetailsMapper {
    @Override
    public UserDetailsResponse apply(User user) {
        return DataUtils.copyProperties(user, UserDetailsResponse.class);
    }
}
