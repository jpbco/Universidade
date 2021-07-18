package com.uevora.sdv.Repository;

import com.uevora.sdv.Entity.StockNacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;import java.util.List;
import java.util.Optional;

@Repository
public interface StockNacionalRepository extends JpaRepository<StockNacional, Long> {

    Optional<StockNacional> findStockNacionalByData(LocalDate data);
    void removeStockNacionalByData(LocalDate data);
}
