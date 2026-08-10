package io.github.Mishaa105.price_tracker.db.repository;

import io.github.Mishaa105.price_tracker.db.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {}
