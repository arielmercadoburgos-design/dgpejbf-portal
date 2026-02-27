package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.Pais;
import dgpejbf.portal.domain.secundaria.Ciudad;
import dgpejbf.portal.repository.secundaria.PaisRepository;
import dgpejbf.portal.repository.secundaria.CiudadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PaisService {

    private final PaisRepository paisRepository;
    private final CiudadRepository ciudadRepository;

    // Constructor para inyectar los repositorios
    public PaisService(PaisRepository paisRepository, CiudadRepository ciudadRepository) {
        this.paisRepository = paisRepository;
        this.ciudadRepository = ciudadRepository;
    }

    // Obtener todos los países
    @Transactional(readOnly = true)
    public List<Pais> listarTodosLosPaises() {
        return paisRepository.findAll();
    }

    // Obtener ciudades de un país específico
    @Transactional(readOnly = true)
    public List<Ciudad> listarCiudadesPorPais(Integer paisId) {
        return ciudadRepository.findByPaisId(paisId);
    }
}
