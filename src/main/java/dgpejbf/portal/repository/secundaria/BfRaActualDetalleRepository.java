package dgpejbf.portal.repository.secundaria;

import dgpejbf.portal.domain.secundaria.BfRaActualDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BfRaActualDetalleRepository extends JpaRepository<BfRaActualDetalle, Integer> {

    // Buscar detalles por RUC de la empresa principal
    @Query("""
        SELECT d FROM BfRaActualDetalle d
        WHERE (:ruc IS NULL OR d.ruc = :ruc)
          AND (:nombre IS NULL OR LOWER(d.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
    """)
    Page<BfRaActualDetalle> buscar(
        @Param("ruc") Integer ruc,
        @Param("nombre") String nombre,
        Pageable pageable
    );

    // Método para traer todos los detalles de una empresa sin paginación (para export)
    List<BfRaActualDetalle> findByRuc(Integer ruc);
}
