package sn.noreyni.issuetrackerbackend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import sn.noreyni.issuetrackerbackend.shared.Constants;

import java.util.Optional;

/**
 * Implementation of {@link org.springframework.data.domain.AuditorAware} based on Spring Security.
 */
@Component
@RequiredArgsConstructor
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM));
    }
}
