package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.auth.SignInRequest;
import vn.edu.nlu.web.chat.dto.auth.request.ResetPasswordRequest;
import vn.edu.nlu.web.chat.dto.auth.response.SignInResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.enums.TokenType;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.UserNotFoundException;
import vn.edu.nlu.web.chat.model.Token;
import vn.edu.nlu.web.chat.model.User;
import vn.edu.nlu.web.chat.repository.UserRepository;
import vn.edu.nlu.web.chat.security.jwt.JwtService;
import vn.edu.nlu.web.chat.service.AuthenticationService;
import vn.edu.nlu.web.chat.service.EmailService;
import vn.edu.nlu.web.chat.service.TokenService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Long NO_LOGIN_USER_ID = -1L;


    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public SignInResponse authenticate(SignInRequest request) {
        try {
            var userDetails = userDetailsService.loadUserByUsername(request.getEmail()); // Check user exists
            var authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationManager.authenticate(authentication);// check enable user or user locked
            String accessToken = jwtService.generateToken(userDetails);
            // additional information v2
            String refreshToken = UUID.randomUUID().toString();
            return new SignInResponse(accessToken,refreshToken);
        } catch (UsernameNotFoundException e) {
            log.error("User not found", e.getMessage());
            throw new ApiRequestException(e.getMessage());
        } catch (DisabledException e) {
            log.error("User is disabled", e);
            throw new ApiRequestException(e.getMessage());
        } catch (Exception e) {
            log.error("Login failed", e);
            throw new ApiRequestException(e.getMessage());  // Generic user-friendly message
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null); // Clear authentication context
        SecurityContextHolder.clearContext(); // Optionally clear entire context
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }
        return null;
    }

    private UserDetails userDetails() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(UserDetails.class::isInstance)
                .map(UserDetails.class::cast)
                .orElse(null);
    }

    @Override
    public Long getCurrentUserId() {
        return Optional.ofNullable(userDetails())
                .filter(userDetails -> userDetails instanceof User)
                .map(userDetails -> ((User) userDetails).getId())
                .orElse(NO_LOGIN_USER_ID);
    }

    @Override
    public void verifyNewUser(String tokenValue) {
        try {
            Token verificationToken = tokenService.getToken(tokenValue);
            if (verificationToken.getEntityStatus() == EntityStatus.INACTIVE) {
                throw new ApiRequestException("Token has used");
            }
            if (tokenService.isTokenExpired(verificationToken)) {
                throw new ApiRequestException("Token expired");
            }

            User user = userRepository.findById(verificationToken.getUserId()).orElseThrow(() -> new ApiRequestException("Email not found"));
            userRepository.save(user);
            verificationToken.setEntityStatus(EntityStatus.INACTIVE);
            verificationToken.setExpiredAt(new Date());
            tokenService.save(verificationToken);
        } catch (RuntimeException ex) {
            throw new ApiRequestException(ex.getMessage());
        }
    }

    @Override
    public void initiatePasswordReset(String username) {
        try {
            User storedUser = getUserByEmail(username);
            Token resetToken = generateResetToken(storedUser);
            tokenService.save(resetToken);
            emailService.sendResetPassword(username, resetToken.getValue());
            log.info("Password reset process initiated for user: {}", username); // Log password reset initiation
        } catch (Exception e) {
            log.error("Error occurred during password reset initiation: {}", e.getMessage(), e); // Log error
            throw e;
        }
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    private Token generateResetToken(User user) {
        return Token.builder()
                .expiredAt(DateUtils.addDays(new Date(), 1))
                .value(UUID.randomUUID().toString())
                .type(TokenType.RESET_PASSWORD)
                .userId(user.getId())
                .build();
    }


    @Override
    public void resetPassword(ResetPasswordRequest request) {
        var storedUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> {
                    log.error("Error occurred during password reset initiation: {}", request.getEmail());
                    return new UserNotFoundException("User with email " + request.getEmail() + " not found");
                });
        storedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(storedUser);
        log.info("Password reset for user with email: {}", request.getEmail());
    }

}