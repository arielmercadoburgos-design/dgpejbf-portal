package dgpejbf.portal.repository.secundaria;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PejRaActualRepository extends JpaRepository<PejRaActual, Integer>, JpaSpecificationExecutor<PejRaActual> {
    Page<PejRaActual> findAll(Pageable pageable);

    // ✅ RUC con paginación
    Page<PejRaActual> findByRuc(Integer ruc, Pageable pageable);

    // ✅ Razón social con paginación (LIKE %%)
    Page<PejRaActual> findByRazonSocialContainingIgnoreCase(String razonSocial, Pageable pageable);

    // ✅ Tipo
    Page<PejRaActual> findByTipoIgnoreCase(String tipo, Pageable pageable);

    // ✅ Fecha
    Page<PejRaActual> findByFechaComunicacionBetween(LocalDateTime desde, LocalDateTime hasta, Pageable pageable);
}
