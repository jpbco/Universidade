package com.uevora.sdv.Controller;

import com.uevora.sdv.Entity.Agendamento;
import com.uevora.sdv.Entity.Centro;
import com.uevora.sdv.Entity.StockDiario;
import com.uevora.sdv.Entity.StockNacional;
import com.uevora.sdv.Service.AgendamentoService;
import com.uevora.sdv.Service.CentroService;
import com.uevora.sdv.Service.StockDiarioService;
import com.uevora.sdv.Service.StockNacionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ApiController {

    private final CentroService centroService;
    private final AgendamentoService agendamentoService;
    private final StockNacionalService stockNacionalService;
    private final StockDiarioService stockDiarioService;

    @Autowired
    public ApiController(
            CentroService centroService,
            AgendamentoService agendamentoService,
            StockNacionalService stockNacionalService,
            StockDiarioService stockDiarioService) {
        this.centroService = centroService;
        this.agendamentoService = agendamentoService;
        this.stockNacionalService = stockNacionalService;
        this.stockDiarioService = stockDiarioService;
    }

    // /UTENTE/

    // consultar centros
    @GetMapping(path = "/utente")
    public List<Centro> consultarCentros(){
        return centroService.getCentros();
    }

    // Agendar a vacinação
    @PostMapping( path = {"/utente"},consumes={"application/json"})
    public void realizarAgendamento(@RequestBody Agendamento agendamento){
        agendamento.setCentro(centroService.findCentroByName(agendamento.getCentroName()));
        agendamentoService.addAgendamento(agendamento);
    }

    // Consultar mensagens: confirmar agendamento:impossibilidade
    @GetMapping(path = "/utente/mensagens/{cc}")
    public String consultarMensagens(@PathVariable String cc){
        System.out.println("mensagens para "+ cc);
        return "TODO";
    }


    // /AGENDAMENTOS/

    @GetMapping(path = "/agendamentos")
    public List<Agendamento> getAgendamentos(){
        return  agendamentoService.getAgendamentos();
    }

    @GetMapping(path = "/agendamentos/data/{data}")
    public List<Agendamento> getAgendamentosByData(@PathVariable String data){
        LocalDate data1 = LocalDate.parse(data);
        return  agendamentoService.getAgendamentos(data1);
    }

    @GetMapping(path = "/agendamentos/centro/{centro}")
    public List<Agendamento> getAgendamentosByCentro(@PathVariable String centro){
        return  agendamentoService.getAgendamentosByCentroName(centro);
    }

    @GetMapping(path = "/agendamentos/centro/{centro}/data/{data}")
    public List<Agendamento> getAgendamentosByCentroAndData(@PathVariable String centro, @PathVariable String data){
        LocalDate data1 = LocalDate.parse(data);
        return  agendamentoService.getAgendamentosByCentroNameAndData(centro,data1);
    }

    // /Vacinados/

    @GetMapping(path = "/vacinados")
    public List<Agendamento> getVacinados(){
        return  agendamentoService.getVacinados();
    }


    @GetMapping(path = "/vacinados/{data}")
    public List<Agendamento> getVacinados(@PathVariable LocalDate data){
        return  agendamentoService.getVacinadosByDate(data);
    }

    @GetMapping(path = "/vacinados/{centro}")
    public List<Agendamento> getVacinados(@PathVariable String centro){
        return  agendamentoService.getVacinadosByCentroName(centro);
    }


    // CENTRO

    // Retornar lista dos centros
    @GetMapping(path = "/centro")
    public List<Centro> getCentro(){
        return centroService.getCentros();
    }

    // Registar novo centro
    @PostMapping(path = "/centro")
    public void addCentro(@RequestBody Centro centro){
        centroService.addNewCentro(centro);
    }

    // Retornar um centro existente
    @GetMapping(path = "/centro/{centroName}")
    public Centro getCentro(@PathVariable("centroName") String centroName){
        return centroService.getCentro(centroName);
    }


    // Stock nacional

    @GetMapping(path = "/stockNacional")
    public List<StockNacional> getStockNacional(){
        return stockNacionalService.getStockNacional();
    }

    @GetMapping(path = "/stockNacional/{data}")
    public StockNacional getStockNacionalByDate(@PathVariable String data){
        LocalDate data1 = LocalDate.parse(data);
        return stockNacionalService.getStockNacionalByDate(data1);
    }

    @PostMapping(path = "/stockNacional")
    public void addStock(@RequestBody StockNacional stock){
        stockNacionalService.addStockNacional(stock);
    }


    // Stock centro

    @GetMapping(path = "/stockDiario")
    public List<StockDiario> getStockDiario(){ return stockDiarioService.getStockDiario();}

    @GetMapping(path = "/stockDiario/data/{data}")
    public List<StockDiario> getStockDiario(@PathVariable String data){
        LocalDate data1 = LocalDate.parse(data);
        return stockDiarioService.getStockDiarioByDate(data1);}

    @GetMapping(path = "/stockDiario/centro/{centro}")
    public List<StockDiario> getStockDiarioCentro(@PathVariable String centro) {
        return stockDiarioService.getStockDiarioByName(centro);
    }

    @GetMapping(path = "/stockDiario/centro/{centro}/data/{data}")
    public StockDiario getStockDiarioCentroData(@PathVariable String centro, @PathVariable String data){
        LocalDate data1 = LocalDate.parse(data);
        return stockDiarioService.getStockDiarioByCentroNameAndData(centro,data1);
    }

    @PostMapping(path = "/stockDiario/centro/{centro}/data/{data}/{vacinas}")
    public void addStockDiario(@PathVariable String centro, @PathVariable String data, @PathVariable int vacinas){
        LocalDate data1 = LocalDate.parse(data);

        stockDiarioService.addStockDiario(new StockDiario(
                data1,
                vacinas,
                centro
        ));
    }

    // Vacinar

    @PutMapping(path = "/vacinar/{cc}")
    public void vacinar(@PathVariable String cc){
        agendamentoService.vacinar(cc);
    }


}
