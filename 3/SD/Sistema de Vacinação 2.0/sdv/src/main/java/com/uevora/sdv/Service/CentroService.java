package com.uevora.sdv.Service;

import com.uevora.sdv.Entity.Centro;
import com.uevora.sdv.Repository.CentroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CentroService {

    private final CentroRepository centroRepository;

    @Autowired
    public CentroService(CentroRepository centroRepository) {
        this.centroRepository = centroRepository;
    }

    public List<Centro> getCentros(){
        return centroRepository.findAll();
    }

    public void addNewCentro(Centro centro) {
        Optional<Centro> centroByName = centroRepository.findCentroByNome(centro.getNome());
        if(centroByName.isPresent()){
            throw new IllegalStateException("Já existe um centro com esse nome.");
        }
            centroRepository.save(centro);

    }


    public void deleteCentro(Long centroId) {
        boolean exists = centroRepository.existsById(centroId);
        if (!exists){
            throw new IllegalStateException("o centro com id " + centroId + " não existe");
        }
        centroRepository.deleteById(centroId);
    }

    public Centro findCentroByName(String centroName) {
        return centroRepository.findCentroByNome(centroName).get();
    }

    public Centro getCentro(String centroName) {
        Optional<Centro> centro = centroRepository.findCentroByNome(centroName);
        if(!centro.isPresent())
            throw new IllegalStateException("Centro não existe");
        return centro.get();
    }

    public List<Centro> getCentrosWithAgendamentos(){
        return centroRepository.findCentrosByAgendamentosNotNull();
    }
}
