package io.github.Mishaa105.price_tracker.db.repository;

import io.github.Mishaa105.price_tracker.db.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {}
