package com.uevora.sdv.Service;

import com.uevora.sdv.Entity.StockNacional;
import com.uevora.sdv.Repository.StockNacionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;import java.util.List;
import java.util.Optional;

@Service
public class StockNacionalService {

    private final StockNacionalRepository stockNacionalRepository;

    @Autowired
    public StockNacionalService(StockNacionalRepository stockNacionalRepository){
        this.stockNacionalRepository = stockNacionalRepository;
    }

    public StockNacional getStockNacionalByDate(LocalDate date){
        return stockNacionalRepository.findStockNacionalByData(date).get();
    }

    public List<StockNacional> getStockNacional(){
        return stockNacionalRepository.findAll();
    }

    @Transactional
    public void addStockNacional(StockNacional stock) {
        Optional<StockNacional> stockNacional = stockNacionalRepository
                .findStockNacionalByData(stock.getData());
        if(stockNacional.isPresent()){
            stockNacionalRepository.removeStockNacionalByData(stock.getData());
            System.out.println("Número de vacinas foi atualizado");
        }
        stockNacionalRepository.save(stock);
    }

}
