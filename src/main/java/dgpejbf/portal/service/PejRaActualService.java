package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import dgpejbf.portal.repository.secundaria.PejRaActualRepository;
import dgpejbf.portal.service.dto.PejRaActualDTO;
import dgpejbf.portal.service.mapper.secundaria.PejRaActualMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PejRaActualService {

    private final PejRaActualRepository repository;
    private PejRaActualMapper mapper;

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

    public List<PejRaActualDTO> obtenerRegistros(int page, int size, String filtro) {
        Sort sort = Sort.by("fechaComunicacion").descending();
        // obtain all entities (avoid repository.findAll(Pageable) which may reference Page)
        List<PejRaActual> entities = repository.findAll(sort);

        // sort by fechaComunicacion descending in-memory
        entities.sort((a, b) -> {
            if (a == null || a.getFechaComunicacion() == null) return 1;
            if (b == null || b.getFechaComunicacion() == null) return -1;
            return b.getFechaComunicacion().compareTo(a.getFechaComunicacion());
        });
        // apply simple in-memory filter if requested
        if (filtro != null && !filtro.isEmpty()) {
            String lower = filtro.toLowerCase();
            entities = entities
                .stream()
                .filter(r -> r.getRazonSocial() != null && r.getRazonSocial().toLowerCase().contains(lower))
                .collect(Collectors.toList());
        }

        // apply simple in-memory paging
        int fromIndex = Math.max(0, Math.min(entities.size(), page * size));
        int toIndex = Math.max(0, Math.min(entities.size(), fromIndex + size));
        List<PejRaActual> paged = entities.subList(fromIndex, toIndex);

        // map to DTOs
        return paged.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
