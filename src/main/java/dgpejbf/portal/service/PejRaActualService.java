package dgpejbf.portal.service;

import dgpejbf.portal.repository.secundaria.PejRaActualRepository;
import dgpejbf.portal.service.dto.PejRaActualDTO;
import dgpejbf.portal.service.mapper.secundaria.PejRaActualMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PejRaActualService {

    private final PejRaActualRepository repository;
    private final PejRaActualMapper mapper;

    public PejRaActualService(PejRaActualRepository repository, PejRaActualMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /** Solo lectura: obtener todos los registros */
    public List<PejRaActualDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /** Solo lectura: obtener por ID */
    public PejRaActualDTO findOne(Integer id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }
}
