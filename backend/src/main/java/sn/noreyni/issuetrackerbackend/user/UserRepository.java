package sn.noreyni.issuetrackerbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneWithRolesByLoginIgnoreCaseOrEmailIgnoreCase(String login, String email);

}