package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.BfRaActual;
import dgpejbf.portal.repository.secundaria.BfRaActualRepository;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDTO;
import dgpejbf.portal.service.mapper.secundaria.BfRaActualMapper;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BfRaActualService {

    private final BfRaActualRepository repository;
    private final BfRaActualMapper mapper;

    public BfRaActualService(BfRaActualRepository repository, BfRaActualMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private Specification<BfRaActual> buildSpecification(String razonSocial, String tipo, Integer ruc) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por RUC
            if (ruc != null) {
                predicates.add(cb.equal(root.get("ruc"), ruc));
            }

            // Filtro por RazonSocial y Tipo combinados
            if ((razonSocial != null && !razonSocial.isBlank()) ||
                (tipo != null && !tipo.isBlank())) {

                String valor = razonSocial != null && !razonSocial.isBlank()
                        ? razonSocial.toLowerCase()
                        : tipo.toLowerCase();

                Predicate razonSocialLike =
                        cb.like(cb.lower(root.get("razonSocial")), "%" + valor + "%");

                Predicate tipoLike =
                        cb.like(cb.lower(root.get("tipo")), "%" + valor + "%");

                predicates.add(cb.or(razonSocialLike, tipoLike));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Buscar con filtros dinámicos y paginación
     */
    public Page<BfRaActualDTO> buscar(String ruc, String razonSocial, String tipo, Pageable pageable) {
        Integer rucAsInteger = null;
        if (ruc != null && !ruc.isBlank()) {
            try {
                rucAsInteger = Integer.valueOf(ruc);
            } catch (NumberFormatException e) {
                // Si falla, se mantiene null
            }
        }

        Specification<BfRaActual> spec = buildSpecification(razonSocial, tipo, rucAsInteger);
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    /**
     * Exportar todos los datos filtrados (sin paginación)
     */
    public List<BfRaActualDTO> exportAll(String razonSocial, String tipo, Integer ruc) {
        Specification<BfRaActual> spec = buildSpecification(razonSocial, tipo, ruc);
        List<BfRaActual> results = repository.findAll(spec);
        return results.stream().map(mapper::toDto).toList();
    }

    /**
     * Obtener un registro por ID
     */
    public BfRaActualDTO findOne(Long id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    /**
     * Obtener todos los registros sin filtros
     */
    public List<BfRaActualDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }
}
