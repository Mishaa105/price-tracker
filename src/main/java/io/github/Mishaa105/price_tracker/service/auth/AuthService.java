package io.github.Mishaa105.price_tracker.service.auth;

import io.github.Mishaa105.price_tracker.dto.auth.RegisterRequest;
import io.github.Mishaa105.price_tracker.entity.Users;
import io.github.Mishaa105.price_tracker.enums.auth.Role;
import io.github.Mishaa105.price_tracker.infrastructure.db.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService
{
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    //TODO Проверка на уникальность

    public void signUp(RegisterRequest request)
    {
        String password = request.password();
        String email = request.email();
        String username = request.username();

        String passwordHash = passwordEncoder.encode(password);

        Users user = new Users(email, passwordHash, username, Role.USER);

        usersRepository.save(user);
        log.info("User {} successfully registered", username);
    }
}
