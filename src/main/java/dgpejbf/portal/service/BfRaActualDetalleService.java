package dgpejbf.portal.service;

import dgpejbf.portal.repository.secundaria.BfRaActualDetalleRepository;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDetalleDTO;
import dgpejbf.portal.service.mapper.secundaria.BfRaActualDetalleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BfRaActualDetalleService {

    private final BfRaActualDetalleRepository repository;
    private final BfRaActualDetalleMapper mapper;

    public BfRaActualDetalleService(
        BfRaActualDetalleRepository repository,
        BfRaActualDetalleMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<BfRaActualDetalleDTO> findByRuc(Integer ruc) {
        return repository.findByRuc(ruc)
            .stream()
            .map(mapper::toDto)
            .toList();
    }
}
