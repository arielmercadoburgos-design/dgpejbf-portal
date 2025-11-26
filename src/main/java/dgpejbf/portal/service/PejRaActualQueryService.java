package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import dgpejbf.portal.repository.secundaria.PejRaActualRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PejRaActualQueryService {

    private final PejRaActualRepository repository;

    public PejRaActualQueryService(PejRaActualRepository repository) {
        this.repository = repository;
    }

    public Page<PejRaActual> buscar(String ruc, String razonSocial, Pageable pageable) {
        Specification<PejRaActual> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ruc != null && !ruc.isBlank()) {
                try {
                    Integer rucInt = Integer.parseInt(ruc.trim());
                    predicates.add(cb.equal(root.get("ruc"), rucInt));
                } catch (NumberFormatException e) {
                    // Si no es número, devuelve vacío
                    predicates.add(cb.disjunction());
                }
            }

            if (razonSocial != null && !razonSocial.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("razonSocial")), "%" + razonSocial.toLowerCase() + "%"));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec, pageable);
    }
}
