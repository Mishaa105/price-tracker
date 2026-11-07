package io.github.Mishaa105.price_tracker.service.auth;

import io.github.Mishaa105.price_tracker.dto.auth.LoginRequest;
import io.github.Mishaa105.price_tracker.dto.auth.LoginResponse;
import io.github.Mishaa105.price_tracker.dto.auth.RegisterRequest;
import io.github.Mishaa105.price_tracker.entity.Users;
import io.github.Mishaa105.price_tracker.enums.auth.Role;
import io.github.Mishaa105.price_tracker.infrastructure.db.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService
{
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    //TODO Проверка на уникальность

    public void signUp(RegisterRequest registerRequest)
    {
        String password = registerRequest.password();
        String email = registerRequest.email();
        String username = registerRequest.username();

        String passwordHash = passwordEncoder.encode(password);

        Users user = new Users(email, passwordHash, username, Role.USER);

        usersRepository.save(user);
        log.info("User {} successfully registered", username);
    }

    public LoginResponse signIn(LoginRequest loginRequest)
    {
        String requestEmail = loginRequest.email();
        String requestPassword = loginRequest.password();
        String jwtToken;

        Users user = usersRepository.findByEmail(requestEmail)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if(passwordEncoder.matches(requestPassword, user.getPassword()))
        {
            jwtToken = jwtService.generateJwt(user.getUserId().toString(), user.getRole().name());
            log.info("Jwt successfully received");
        }
        else
        {
            jwtToken = null;
            log.info("Jwt not received");
        }

        return new LoginResponse(jwtToken);
    }
}
