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
     * La firma es (String razonSocial, String tipo, Integer ruc)
     * @param razonSocial Razón Social como String.
     * @param tipo Tipo como String.
     * @param ruc RUC como Integer.
     * @return Specification para JPA.
     */
    private Specification<PejRaActual> buildSpecification(String razonSocial, String tipo, Integer ruc) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 1. Filtro por RUC (usando Integer)
            if (ruc != null) {
                predicates.add(cb.equal(root.get("ruc"), ruc));
            }
            
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
     * Buscar con filtros dinámicos y paginación.
     */
    public Page<PejRaActualDTO> buscar(String ruc, String razonSocial, String tipo, Pageable pageable) {
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
        Specification<PejRaActual> spec = buildSpecification(razonSocial, tipo, rucAsInteger);

        return repository.findAll(spec, pageable).map(mapper::toDto);
    }
    
    /**
     * Exportar todos los datos filtrados (no paginados).
     * FIRMA REQUERIDA POR EL RECURSO: (String razonSocial, String tipo, Integer ruc)
     */
    public List<PejRaActualDTO> exportAll(String razonSocial, String tipo, Integer ruc) {
        // Usamos la misma Specification pero con los tipos correctos (razonSocial=String, ruc=Integer)
        Specification<PejRaActual> spec = buildSpecification(razonSocial, tipo, ruc);

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