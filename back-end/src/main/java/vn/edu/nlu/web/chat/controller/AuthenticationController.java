package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.auth.SignInRequest;
import vn.edu.nlu.web.chat.dto.auth.request.ResetPasswordRequest;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.service.AuthenticationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:3000")
@RequestMapping(path = "/api/v1/auth")
@Tag(name = "Authentication Controller")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Operation(summary = "Sign In", description = "Authenticate and sign in a user.")
    @PostMapping(path = "/sign-in")
    public ApiResponse<?> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("Received login request for user: {}", request.getEmail());
        try {
            var res = authenticationService.authenticate(request);
            log.info("Login successful for user: {}", request.getEmail());
            return new ApiResponse<>(HttpStatus.ACCEPTED, Translator.toLocale("auth.login.success"), res);
        } catch (ApiRequestException e) {
            log.error("Login failed", e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred during login for user: {}", request.getEmail(), e);
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }


    @Operation(summary = "Logout", description = "Log out the currently authenticated user.")
    @PostMapping(path = "/sign-out")
    public ApiResponse<?> logout() {
        try {
            log.info("Received logout request");
            authenticationService.logout();
            log.info("Logout successful");
            return new ApiResponse<>(HttpStatus.NO_CONTENT, Translator.toLocale("auth.logout.success"));

        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("An error occurred during logout: {}", e.getMessage());
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during logout");
        }
    }

    @Operation(summary = "Verify New User", description = "Verify a new user by token.")
    @GetMapping(path = "verify-new-user")
    public ApiResponse<?> verifyNewUserByToken(@RequestParam String token) {
        try {
            authenticationService.verifyNewUser(token);
            log.info("User verification successful for token: {}", token);
            return new ApiResponse(HttpStatus.NO_CONTENT, "Verify new user successful");
        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("An error occurred during user verification for token {}: {}", token, e.getMessage());
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during user verification");
        }
    }

    @Operation(summary = "Forgot Password", description = "Initiate the password reset process for a user.")
    @PostMapping(path = "/forgot-password")
    public ApiResponse<?> forgotPassword(@RequestParam String username) {
        try {
            log.info("Received forgot password request for user: {}", username);
            authenticationService.initiatePasswordReset(username);
            log.info("Forgot password request successful for user: {}", username);
            return new ApiResponse<>(HttpStatus.NO_CONTENT, "Forgot password reset successful");
        } catch (Exception e) {
            log.error("An error occurred during forgot password process for user {}: {}", username, e.getMessage());
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during forgot password process");
        }
    }

    @Operation(summary = "Reset Password", description = "Reset the password for a user.")
    @PatchMapping(path = "/reset-password")
    public ApiResponse<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        log.info("Received password reset request for user: {}", request.getEmail());
        try {
            authenticationService.resetPassword(request);
            log.info("Password reset successfully for user: {}", request.getEmail());
            return new ApiResponse(HttpStatus.ACCEPTED, "Password reset successful");
        } catch (Exception e) {
            log.error("Error occurred during password reset for user {}: {}", request.getEmail(), e.getMessage());
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred during password reset for user " + request.getEmail());
        }
    }


}