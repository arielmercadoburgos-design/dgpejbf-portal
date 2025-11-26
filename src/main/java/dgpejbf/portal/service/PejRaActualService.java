package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import dgpejbf.portal.repository.secundaria.PejRaActualRepository;
import dgpejbf.portal.service.dto.PejRaActualDTO;
import dgpejbf.portal.service.mapper.secundaria.PejRaActualMapper;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PejRaActualService {

    private final PejRaActualRepository repository;
    private final PejRaActualMapper mapper;

    public PejRaActualService(PejRaActualRepository repository, PejRaActualMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Buscar con filtros dinámicos
     */
    public Page<PejRaActualDTO> buscar(String ruc, String razonSocial, Pageable pageable) {
        Specification<PejRaActual> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ruc != null && !ruc.isBlank()) {
                predicates.add(cb.equal(root.get("ruc"), Integer.valueOf(ruc)));
            }
            if (razonSocial != null && !razonSocial.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("razonSocial")), "%" + razonSocial.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    /**
     * Obtener un registro por ID
     */
    public PejRaActualDTO findOne(Integer id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    /**
     * Obtener todos sin filtros (si lo necesitás)
     */
    public List<PejRaActualDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }
}
