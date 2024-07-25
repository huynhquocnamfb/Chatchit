package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.auth.SignInRequest;
import vn.edu.nlu.web.chat.dto.auth.request.ResetPasswordRequest;
import vn.edu.nlu.web.chat.dto.auth.response.SignInResponse;

/**
 * Service interface providing common functionalities related to user authentication.
 *
 * @author MENES
 * @version 0.0.1
 * @since 12/30/2023
 */
public interface AuthenticationService {

    /**
     * Performs user authentication.
     *
     * @param loginRequest the login request containing user credentials
     * @return the response containing authentication result
     */
    SignInResponse authenticate(SignInRequest loginRequest);

    /**
     * Logs the currently authenticated user out of the chat system.
     */
    void logout();

    /**
     * Checks if a user is currently authenticated.
     *
     * @return true if a user is authenticated, false otherwise
     */
    boolean isAuthenticated();

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * @return the username if authenticated, null otherwise
     */
    String getCurrentUsername();

    /**
     * Retrieves the user ID of the currently authenticated user.
     *
     * @return the user ID if authenticated, null otherwise
     */
    Long getCurrentUserId();

    /**
     * Verifies a new user registration using the provided token.
     *
     * @param token the verification token
     */
    void verifyNewUser(String token);

    /**
     * Initiates the password reset process for the specified username.
     *
     * @param username the username for which password reset is initiated
     */
    void initiatePasswordReset(String username);

    /**
     * Resets the password using the provided reset password request.
     *
     * @param resetPasswordRequest the reset password request
     */
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
