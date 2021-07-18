package com.uevora.sdv.Repository;

import com.uevora.sdv.Entity.StockDiario;
import com.uevora.sdv.Entity.StockNacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockDiarioRepository extends JpaRepository<StockDiario, Long> {
    List<StockDiario> findStockDiariosByData(LocalDate data);
    List<StockDiario> findStockDiariosByCentroName(String centro);
    Optional<StockDiario> findStockDiarioByCentroNameAndData(String centro, LocalDate date);
    Optional<StockDiario> findStockDiarioByData(LocalDate data);
    void removeStockDiarioByCentroNameAndData(String centro, LocalDate data);
}
