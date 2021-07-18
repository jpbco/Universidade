package com.uevora.sdv.Service;

import com.uevora.sdv.Entity.Agendamento;
import com.uevora.sdv.Entity.Centro;
import com.uevora.sdv.Entity.StockDiario;
import com.uevora.sdv.Entity.StockNacional;
import com.uevora.sdv.Repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final StockDiarioService stockDiarioService;
    private final StockNacionalService stockNacionalService;
    private final CentroService centroService;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              StockDiarioService stockDiarioService,
                              StockNacionalService stockNacionalService,
                              CentroService centroService
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.stockDiarioService = stockDiarioService;
        this.stockNacionalService = stockNacionalService;
        this.centroService = centroService;
    }

    public void addAgendamento(Agendamento agendamento) {
        Optional<Agendamento> agendamentoByCc = agendamentoRepository.findAgendamentoByCcAndVacinadoFalse(agendamento.getCc());

        if (agendamentoByCc.isPresent() && agendamentoByCc.get().isVacinado()) {
            throw new IllegalStateException("Esta pessoa já se encontra vacinada!");
        }

        if (agendamentoByCc.isPresent() && !agendamentoByCc.get().isVacinado()) {
            throw new IllegalStateException("Esta pessoa já realizou o agendamento: " + agendamentoByCc.get().getData().toString());
        }

        agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> getAgendamentos(LocalDate data) {
        return agendamentoRepository.findAgendamentosByDataAndVacinadoIsFalse(data);
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentoRepository.findAllByVacinadoIsFalse();
    }

    public List<Agendamento> getVacinadosByDate(LocalDate data) {
        return agendamentoRepository.findAgendamentosByDataAndVacinadoIsTrue(data);
    }

    public List<Agendamento> getVacinadosByCentroName(String centro) {
        return agendamentoRepository.findAgendamentosByCentroNameAndVacinadoIsTrue(centro);
    }

    public List<Agendamento> getVacinados() {
        return agendamentoRepository.findAgendamentosByVacinadoIsTrue();
    }

    public List<Agendamento> getVacinados(String centro) {
        return agendamentoRepository.findAgendamentosByCentroNameAndVacinadoIsTrue(centro);
    }

    public List<Agendamento> getVacinadosByCentroNameAndData(String centro, LocalDate data) {
        return agendamentoRepository.findAgendamentosByCentroNameAndDataAndVacinadoIsTrue(centro, data);
    }

    public List<Agendamento> getAgendamentosByCentroName(String centro) {
        return agendamentoRepository.findAgendamentosByCentroNameAndVacinadoIsFalse(centro);
    }

    public List<Agendamento> getAgendamentosByCentroNameAndData(String centro, LocalDate data1) {
        return agendamentoRepository.findAgendamentosByCentroNameAndDataAndVacinadoIsFalse(centro, data1);
    }

    @Transactional
    public void vacinar(String cc) {

        Agendamento agendamento = agendamentoRepository.findAgendamentoByCcAndVacinadoFalse(cc)
                .orElseThrow(
                        () -> new IllegalStateException("Cartão de cidadão não encontrado")
                );
        if (agendamento.isVacinado()) {
            throw new IllegalStateException("Esta pessoa não se encontra agendada.");
        }
        LocalDate dataAgendamento = agendamento.getData();
        String centroN = agendamento.getCentroName();
        StockDiario stock = stockDiarioService.getStockDiarioByCentroNameAndData(centroN, dataAgendamento);

        if (stock.getVacinas() < 1) {
            throw new IllegalStateException("Não há mais vacinas para" + dataAgendamento);
        }
        stock.setVacinas(stock.getVacinas() - 1);
        agendamento.setVacinado(true);
        agendamento.setData_vacinacao(LocalDate.now());

    }

    @Transactional
    public void distribuirVacinas(LocalDate data) {
        LocalDate now = LocalDate.now();
        if(data.compareTo(now) < 0)
            throw new IllegalStateException("Não pode distribuir vacinas numa data passada...");

        StockNacional stockNacional = stockNacionalService.getStockNacionalByDate(data);
        List<Centro> centros = centroService.getCentrosWithAgendamentos();

        int vacinasTotal = stockNacional.getVacinas();
        int vacinasPorCentro = vacinasTotal/centros.size();

        for (Centro centro: centros) {
            int capacidade = centro.getCapacidade_diaria();
            List<Agendamento> agendamentos = centro.getAgendamentos();
            List<Agendamento> agendamentosHoje = agendamentos.stream().filter(a -> a.getData()==data).toList();
            System.out.println(agendamentos);
            System.out.println(agendamentosHoje);
        }
    }
}
