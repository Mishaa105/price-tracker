package io.github.Mishaa105.price_tracker.infrastructure.db.repository;

import io.github.Mishaa105.price_tracker.entity.Currency;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurrencyRepository extends JpaRepository<Currency, Integer>
{
    @Transactional
    @Modifying
    @Query("UPDATE Currency c SET c.exchangeRate = :newRate WHERE c.currency = :currencyName")
    int updateExchangeRatesByCurrency(@Param("currencyName") String currencyName, @Param("newRate") Double newRate);

    @Transactional
    @Modifying
    @Query("INSERT INTO Currency(currency, exchangeRate) VALUES(:currencyName, :rate)")
    void createNewCurrencyWithExchangeRate(@Param("currencyName") String currencyName, @Param("rate") Double rate);
}
