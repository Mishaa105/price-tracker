package io.github.Mishaa105.price_tracker.db.repository;

import io.github.Mishaa105.price_tracker.db.entity.StoreClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreClassificationRepository extends JpaRepository<StoreClassification, Integer> {}
