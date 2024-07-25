package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.config.locale.Translator;

import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.details.BaseUserDTO;
import vn.edu.nlu.web.chat.dto.details.BasicDetailsDTO;
import vn.edu.nlu.web.chat.dto.details.ProfileDTO;
import vn.edu.nlu.web.chat.dto.user.request.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.user.request.UserUpdateRequest;
import vn.edu.nlu.web.chat.dto.user.response.UserCreateResponse;
import vn.edu.nlu.web.chat.dto.user.response.UserDetailsResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.enums.SocialStatus;
import vn.edu.nlu.web.chat.enums.TokenType;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.exception.UserNotFoundException;
import vn.edu.nlu.web.chat.model.Token;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.service.AuthenticationService;
import vn.edu.nlu.web.chat.service.EmailService;
import vn.edu.nlu.web.chat.service.TokenService;
import vn.edu.nlu.web.chat.service.UserService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @Override
    public boolean exists(Long id) {
        return userRepository.findByIdAndEntityStatusNotDeleted(id).isPresent();
    }

    @Override
    public Long getIdByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("No such user id")).getId();
    }

    @Override
    public boolean exists(String username) {
        User storedUser = userRepository.findByEmail(username).orElseThrow(() -> new ApiRequestException("User not found"));
        return exists(storedUser.getId());
    }

    @Override
    public UserCreateResponse create(UserCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.error("Email already exists: {}", request.getEmail());
            throw new ApiRequestException("Email taken!");
        }
        try {
            User newUser = DataUtils.copyProperties(request, User.class);
            newUser.setSocialStatus(SocialStatus.OFFLINE);
            newUser.setAppName(request.getEmail().split("@")[0]);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);

            Token verificationToken = Token.builder()
                    .userId(newUser.getId())
                    .type(TokenType.VERIFICATION)
                    .expiredAt(DateUtils.addDays(new Date(), 1))
                    .value(UUID.randomUUID().toString())
                    .build();

            tokenService.save(verificationToken);
            emailService.sendVerificationNewUser(newUser.getEmail(), verificationToken.getValue());
            return DataUtils.copyProperties(newUser, UserCreateResponse.class);

        } catch (ResourceNotFoundException e) {
            throw new ApiRequestException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.add.fail"));
        }


    }

    @Override
    public PageResponse<?> getAllUsersWithPagingAndSorting(int pageNo, int pageSize, String sortBy) {
        return getAllUsersAndSearchWithPagingAndSorting(pageNo, pageSize, "", sortBy);
    }

    @Override
    public void delete(long userId) {
        try {
            User storedUser = getUserById(userId);
            storedUser.setEntityStatus(EntityStatus.DELETED);
            userRepository.save(storedUser);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.delete.fail"));
        }
    }

    @Override
    public void update(long userId, UserUpdateRequest request) {
        try {
            User storedUser = getUserById(userId);

            userRepository.save(storedUser);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.update.fail"));
        }

    }

    @Override
    public UserDetailsResponse getUserDetailsById(long userId) {
        try {
            User storedUser = getUserById(userId);
            return DataUtils.copyProperties(storedUser, UserDetailsResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.get.fail"));
        }
    }

    @Override
    public PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy) {
        try {
            pageNo = pageNo > 1 ? pageNo - 1 : 0;
            return userRepository.searchUsersWithPaginationAndSorting(pageNo, pageSize, search, sortBy);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiRequestException(Translator.toLocale("user.list.fail"));
        }
    }

    @Override
    public ProfileDTO getProfileById(Long userId) {
        if (!exists(userId)) {
            throw new UserNotFoundException("User not found");
        }
        var storedUser = getUserById(userId);
        var basicDetailsDTO = BasicDetailsDTO.builder()
                .email(storedUser.getEmail())
                .title("Dummy title")
                .avatar(storedUser.getAvatarUrl())
                .coverImage("https://th.bing.com/th/id/OIP.eXN0mP93BMDEOdYa3dK3zwHaEK?w=333&h=187&c=7&r=0&o=5&pid=1.7")
                .description(storedUser.getDescription())
                .location(storedUser.getLocation())
                .firstName(storedUser.getFirstName())
                .lastName(storedUser.getLastName())
                .build();
        return ProfileDTO.builder()
                .basicDetails(basicDetailsDTO)
                .status("Away")
                .build();
    }

    @Override
    public ProfileDTO getMyProfile() {
        Long currentUserId = authenticationService.getCurrentUserId();
        return getProfileById(currentUserId);
    }

    @Override
    public BaseUserDTO getMe() {
        var userProfile = getMyProfile();
        return BaseUserDTO.builder()
                .id(authenticationService.getCurrentUserId())
                .profileImage(userProfile.getBasicDetails().getAvatar())
                .build();
    }

    private User getUserById(long id) {
        return userRepository.findByIdAndEntityStatusNotDeleted(id)
                .orElseThrow(() -> {
                    String msg = "User with id " + id + " not found";
                    log.error(msg);
                    return new ResourceNotFoundException(msg);
                });
    }
}
