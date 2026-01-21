package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.PejRaActualDirectivo;
import dgpejbf.portal.repository.secundaria.PejRaActualDirectivoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PejRaActualDirectivoService {

    @Autowired
    private PejRaActualDirectivoRepository repository;

    public List<PejRaActualDirectivo> obtenerTodos() {
        return repository.findAll();
    }

    public List<PejRaActualDirectivo> buscarPorRuc(Integer ruc) {
        return repository.findByRuc(ruc);
    }

    public PejRaActualDirectivo guardar(PejRaActualDirectivo directivo) {
        return repository.save(directivo);
    }
}