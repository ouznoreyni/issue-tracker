package sn.noreyni.issuetrackerbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sn.noreyni.issuetrackerbackend.shared.Constants;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        var securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();

        if (authentication == null)
            // Replace default user 'anonymousUser' with default value not working, we shall look into it after
            return Optional.of(Constants.SYSTEM);

        var principal = authentication.getName();

        if (principal == null)
            return Optional.of(Constants.SYSTEM);
        return Optional.of(principal);

    }
}

