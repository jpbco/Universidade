package com.uevora.sdv.Repository;

import com.uevora.sdv.Entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findAgendamentoByCcAndVacinadoFalse(String cc);
    List<Agendamento> findAllByVacinadoIsFalse();
    List<Agendamento> findAgendamentosByDataAndVacinadoIsTrue(LocalDate data);
    List<Agendamento> findAgendamentosByVacinadoIsTrue();
    List<Agendamento> findAgendamentosByCentroNameAndVacinadoIsTrue(String centroName);
    List<Agendamento> findAgendamentosByDataAndVacinadoIsFalse(LocalDate data);
    List<Agendamento> findAgendamentosByCentroNameAndVacinadoIsFalse(String centro);
    List<Agendamento> findAgendamentosByCentroNameAndDataAndVacinadoIsFalse(String centro, LocalDate data1);
    List<Agendamento> findAgendamentosByCentroNameAndDataAndVacinadoIsTrue(String centro, LocalDate data);
}
