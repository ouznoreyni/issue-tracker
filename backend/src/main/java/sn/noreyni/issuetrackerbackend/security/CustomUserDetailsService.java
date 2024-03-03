package sn.noreyni.issuetrackerbackend.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sn.noreyni.issuetrackerbackend.exception.exceptions.ApiErrorException;
import sn.noreyni.issuetrackerbackend.role.Role;
import sn.noreyni.issuetrackerbackend.user.User;
import sn.noreyni.issuetrackerbackend.user.UserRepository;

import java.util.List;

/**
 * Authenticate a user from the database.
 */
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername {}", username);
        return userRepository
                .findOneWithRolesByLoginIgnoreCaseOrEmailIgnoreCase(username, username)
                .map(user -> createSpringSecurityUser(username, user))
                .orElseThrow(() -> ApiErrorException.builder()
                        .responseMessage("User does not exist")
                        .responseCode(HttpStatus.FORBIDDEN)
                        .build());
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        log.info("createSpringSecurityUser method called");
        if (!user.isActivated()) {
            throw  ApiErrorException.builder()
                    .responseMessage("User is not activated")
                    .responseCode(HttpStatus.FORBIDDEN)
                    .build();
        }
        List<SimpleGrantedAuthority> grantedAuthorities =
                user
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }
}
