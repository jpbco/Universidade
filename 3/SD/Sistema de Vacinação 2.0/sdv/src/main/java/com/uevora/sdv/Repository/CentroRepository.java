package com.uevora.sdv.Repository;

import com.uevora.sdv.Entity.Centro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CentroRepository extends JpaRepository<Centro, Long> {

    Optional<Centro> findCentroByNome(String nome);
    List<Centro> findCentrosByAgendamentosNotNull();
}
