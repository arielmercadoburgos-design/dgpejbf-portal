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
     * Helper para crear la Specification para los filtros.
     * La firma es (String razonSocial, Integer ruc)
     * @param razonSocial Razón Social como String.
     * @param ruc RUC como Integer.
     * @return Specification para JPA.
     */
    private Specification<PejRaActual> buildSpecification(String razonSocial, Integer ruc) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 1. Filtro por RUC (usando Integer)
            if (ruc != null) {
                predicates.add(cb.equal(root.get("ruc"), ruc));
            }
            
            // 2. Filtro por Razón Social (usando String)
            if (razonSocial != null && !razonSocial.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("razonSocial")), "%" + razonSocial.toLowerCase() + "%"));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    
    /**
     * Buscar con filtros dinámicos y paginación.
     */
    public Page<PejRaActualDTO> buscar(String ruc, String razonSocial, Pageable pageable) {
        // Conversión de RUC de String a Integer para usar en la Specification
        Integer rucAsInteger = null;
        if (ruc != null && !ruc.isBlank()) {
             try {
                // NOTA: Si el RUC no es un número, NumberFormatException se lanza y rucAsInteger se mantiene null.
                rucAsInteger = Integer.valueOf(ruc);
            } catch (NumberFormatException e) {
                // Si falla la conversión, se mantiene null.
            }
        }
        
        // Llamamos al método helper
        Specification<PejRaActual> spec = buildSpecification(razonSocial, rucAsInteger);

        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
    
    /**
     * Exportar todos los datos filtrados (no paginados).
     * FIRMA REQUERIDA POR EL RECURSO: (String razonSocial, Integer ruc)
     */
    public List<PejRaActualDTO> exportAll(String razonSocial, Integer ruc) {
        // Usamos la misma Specification pero con los tipos correctos (razonSocial=String, ruc=Integer)
        Specification<PejRaActual> spec = buildSpecification(razonSocial, ruc);

        // Llamamos a findAll sin Pageable para obtener todos los resultados
        List<PejRaActual> results = repository.findAll(spec);
        
        // Mapeamos y retornamos la lista completa
        return results.stream().map(mapper::toDto).toList();
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