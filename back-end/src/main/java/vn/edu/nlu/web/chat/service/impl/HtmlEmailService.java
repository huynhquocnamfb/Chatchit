package vn.edu.nlu.web.chat.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.edu.nlu.web.chat.service.EmailService;

@Primary
@Service
@RequiredArgsConstructor
public class HtmlEmailService implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.name}")
    private String applicationName;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.endpoint.security.verify-new-user}")
    private String verificationUrl;

    @Value("${app.endpoint.security.reset-password}")
    private String resetPasswordUrl;

    private static final String EMAIL_SUBJECT = "Verification New User";
    private static final String VERIFICATION_TEMPLATE_PATH = "email-verification.html";

    private static final String RESET_PASSWORD_TEMPLATE_PATH = "email-reset-password.html";


    @Async
    @Override
    public void sendVerificationNewUser(String recipientEmail, String verificationToken) {
        Context context = new Context();
        context.setVariable("url", verificationUrl + "?token=" + verificationToken);
        context.setVariable("title", applicationName);
        context.setVariable("name", recipientEmail);

        String emailContent = templateEngine.process(VERIFICATION_TEMPLATE_PATH, context);
        sendEmail(recipientEmail, EMAIL_SUBJECT, emailContent);
    }

    @Override
    public void sendResetPassword(String recipientEmail, String resetPasswordToken) {
        Context context = new Context();
        context.setVariable("title", applicationName);
        context.setVariable("name", recipientEmail);  // Optional, recipient name
        context.setVariable("resetUrl", resetPasswordUrl + "?token=" + resetPasswordToken);
        context.setVariable("validityHours", 24);  // Optional, validity period
        String emailContent = templateEngine.process(RESET_PASSWORD_TEMPLATE_PATH, context);


        sendEmail(recipientEmail, "Reset Your Password", emailContent);
    }

    private void sendEmail(String recipientEmail, String subject, String emailContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setTo(recipientEmail);
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception gracefully, possibly log it
            throw new RuntimeException("Failed to send email: " + subject, e);
        }
    }
}