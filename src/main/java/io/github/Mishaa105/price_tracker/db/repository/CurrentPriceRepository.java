package io.github.Mishaa105.price_tracker.db.repository;

import io.github.Mishaa105.price_tracker.db.entity.CurrentPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentPriceRepository extends JpaRepository<CurrentPrice, Long> {}
