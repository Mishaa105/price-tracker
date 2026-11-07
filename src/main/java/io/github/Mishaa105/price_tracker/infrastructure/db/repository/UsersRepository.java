package io.github.Mishaa105.price_tracker.infrastructure.db.repository;


import io.github.Mishaa105.price_tracker.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID>
{
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
