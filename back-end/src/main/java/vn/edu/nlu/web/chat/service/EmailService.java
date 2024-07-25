package vn.edu.nlu.web.chat.service;

/**
 * Service interface for sending emails related to user authentication and password reset.
 */
public interface EmailService {

    /**
     * Sends a verification email to the specified email address with the given verification token.
     *
     * @param email             The email address to which the verification email will be sent.
     * @param verificationToken The verification token to include in the email.
     */
    void sendVerificationNewUser(String email, String verificationToken);

    /**
     * Sends a reset password email to the specified email address with the given reset password token.
     *
     * @param email              The email address to which the reset password email will be sent.
     * @param resetPasswordToken The reset password token to include in the email.
     */
    void sendResetPassword(String email, String resetPasswordToken);

}