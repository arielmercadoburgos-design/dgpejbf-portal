package dgpejbf.portal.repository.secundaria;

import dgpejbf.portal.domain.secundaria.BfRaActual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BfRaActualRepository extends JpaRepository<BfRaActual, Long>, JpaSpecificationExecutor<BfRaActual> {

    Page<BfRaActual> findByRuc(Integer ruc, Pageable pageable);

    Page<BfRaActual> findByRazonSocialContainingIgnoreCase(String razonSocial, Pageable pageable);

    Page<BfRaActual> findByTipoIgnoreCase(String tipo, Pageable pageable);

    Page<BfRaActual> findByFechaComunicacionBetween(LocalDateTime desde, LocalDateTime hasta, Pageable pageable);

}
