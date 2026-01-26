package dgpejbf.portal.repository.secundaria;

import dgpejbf.portal.domain.secundaria.BfRaActual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BfRaActualRepository extends JpaRepository<BfRaActual, Integer> {

    // Método de búsqueda con filtros opcionales (igual que PejRaActual)
    @Query("""
        SELECT b FROM BfRaActual b
        WHERE (:ruc IS NULL OR b.ruc = :ruc)
          AND (:razonSocial IS NULL OR LOWER(b.razonSocial) LIKE LOWER(CONCAT('%', :razonSocial, '%')))
          AND (:tipoComunicacion IS NULL OR b.tipoComunicacion = :tipoComunicacion)
    """)
    Page<BfRaActual> buscar(
        @Param("ruc") Integer ruc,
        @Param("razonSocial") String razonSocial,
        @Param("tipoComunicacion") String tipoComunicacion,
        Pageable pageable
    );
}
