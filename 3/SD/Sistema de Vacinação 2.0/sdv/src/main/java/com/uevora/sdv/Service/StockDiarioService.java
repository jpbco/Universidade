package com.uevora.sdv.Service;

import com.uevora.sdv.Entity.Centro;
import com.uevora.sdv.Entity.StockDiario;
import com.uevora.sdv.Entity.StockNacional;
import com.uevora.sdv.Repository.StockDiarioRepository;
import com.uevora.sdv.Repository.StockDiarioRepository;
import com.uevora.sdv.Repository.StockNacionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;import java.util.List;
import java.util.Optional;

@Service
public class StockDiarioService {

    private final StockDiarioRepository stockDiarioRepository;
    private final StockNacionalService stockNacionalService;
    private final CentroService centroService;

    @Autowired
    public StockDiarioService(StockDiarioRepository stockDiarioRepository,
                              StockNacionalService stockNacionalService,
                              CentroService centroService){
        this.stockDiarioRepository = stockDiarioRepository;
        this.stockNacionalService = stockNacionalService;
        this.centroService = centroService;
    }

    public List<StockDiario> getStockDiarioByDate(LocalDate date){
        return stockDiarioRepository.findStockDiariosByData(date);
    }

    public List<StockDiario> getStockDiarioByName(String centro){
        return stockDiarioRepository.findStockDiariosByCentroName(centro);

    }
    public List<StockDiario> getStockDiario(){
        return stockDiarioRepository.findAll();
    }

    @Transactional
    public void addStockDiario(StockDiario stock) {
        Optional<StockDiario> stockDiario = stockDiarioRepository
                .findStockDiarioByCentroNameAndData(stock.getCentroName(),stock.getData());
        StockNacional stockN = stockNacionalService.getStockNacionalByDate(stock.getData());
        Centro centro = centroService.findCentroByName(stock.getCentroName());

        int vacinasAdicionais = stock.getVacinas();
        int vacinasMaxCentro = centro.getCapacidade_diaria();
        int vacinasAtuais = 0;
        if(stockDiario.isPresent())
         vacinasAtuais = stockDiario.get().getVacinas();

        if(vacinasAdicionais < vacinasMaxCentro
                && vacinasAdicionais+vacinasAtuais<= vacinasMaxCentro){
            stockDiarioRepository.removeStockDiarioByCentroNameAndData(stock.getCentroName(),stock.getData());
            stockN.setVacinas(stockN.getVacinas()-stock.getVacinas());
            stockDiarioRepository.save(stock);
            System.out.println("Número de vacinas foi atualizado");
        }else{
            throw new IllegalStateException("Não foi possível adicionar vacinas.");
        }

    }

    public StockDiario getStockDiarioByCentroNameAndData(String centro, LocalDate data) {
        return stockDiarioRepository.findStockDiarioByCentroNameAndData(centro,data)
                .orElseThrow(
                        () -> new IllegalStateException("Não há vacinas para esse dia")

        );
    }
}
