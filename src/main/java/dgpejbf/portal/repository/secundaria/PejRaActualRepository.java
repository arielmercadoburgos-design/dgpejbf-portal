package dgpejbf.portal.repository.secundaria;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PejRaActualRepository extends JpaRepository<PejRaActual, Integer> {
    // Filtrar por RUC (coincidencia exacta)
    List<PejRaActual> findByRuc(Integer ruc);

    // Filtrar por razón social (contiene, case-insensitive)
    List<PejRaActual> findByRazonSocialContainingIgnoreCase(String razonSocial);

    // Filtrar por tipo (igual exacto o parcial)
    List<PejRaActual> findByTipoIgnoreCase(String tipo);

    // Filtrar por rango de fechas
    List<PejRaActual> findByFechaComunicacionBetween(LocalDateTime desde, LocalDateTime hasta);
}
