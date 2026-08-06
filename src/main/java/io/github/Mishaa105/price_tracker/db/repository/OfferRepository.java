package io.github.Mishaa105.price_tracker.db.repository;

import io.github.Mishaa105.price_tracker.db.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {}
