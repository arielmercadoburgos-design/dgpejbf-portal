package dgpejbf.portal.repository.secundaria;

import dgpejbf.portal.domain.secundaria.PejRaActualDirectivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PejRaActualDirectivoRepository extends JpaRepository<PejRaActualDirectivo, Integer> {
    
    // De acuerdo a tu tabla, el campo es 'ruc'. 
    // Si en tu entidad el atributo se llama 'ruc', el método debe ser:
    List<PejRaActualDirectivo> findByRuc(Integer ruc);

    // Si necesitas buscar por el ID de la tabla (PK)
    Optional<PejRaActualDirectivo> findByTablaId(Integer tablaId);
}