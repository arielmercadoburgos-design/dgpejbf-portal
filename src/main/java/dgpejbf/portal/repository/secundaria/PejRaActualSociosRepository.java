package dgpejbf.portal.repository.secundaria;


import dgpejbf.portal.domain.secundaria.PejRaActualSocios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PejRaActualSociosRepository extends JpaRepository<PejRaActualSocios, Integer> {

    /**
     * Busca la lista de socios asociados a un RUC específico.
     * Este es el método que usará tu botón de la grilla principal.
     */
    List<PejRaActualSocios> findByRuc(Integer ruc);

    /**
     * Opcional: Si prefieres buscar por el ID del trámite.
     */
    List<PejRaActualSocios> findByTramiteId(Integer tramiteId);
}