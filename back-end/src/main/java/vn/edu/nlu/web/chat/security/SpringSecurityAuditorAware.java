package vn.edu.nlu.web.chat.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import vn.edu.nlu.web.chat.service.AuthenticationService;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    private final AuthenticationService authenticationService;

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(authenticationService.getCurrentUserId());
    }
}