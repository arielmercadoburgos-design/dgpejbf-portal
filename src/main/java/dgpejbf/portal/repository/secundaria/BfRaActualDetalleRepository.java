package dgpejbf.portal.repository.secundaria;

import dgpejbf.portal.domain.secundaria.BfRaActualDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BfRaActualDetalleRepository extends JpaRepository<BfRaActualDetalle, Integer>, JpaSpecificationExecutor<BfRaActualDetalle> {

    // ✅ Buscar integrantes por RUC (útil para ver quiénes componen la sociedad actual)
    List<BfRaActualDetalle> findByRuc(Integer ruc);

    // ✅ Buscar por Trámite ID (esencial para la grilla de detalles de una comunicación específica)
    List<BfRaActualDetalle> findByTramiteId(Integer tramiteId);
    

    // ✅ Buscar por Cédula (específico para encontrar personas físicas)
    Page<BfRaActualDetalle> findByCedula(String cedula, Pageable pageable);

}