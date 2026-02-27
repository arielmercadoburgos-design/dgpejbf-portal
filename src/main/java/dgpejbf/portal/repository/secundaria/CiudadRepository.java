package dgpejbf.portal.repository.secundaria;
import org.springframework.data.jpa.repository.JpaRepository;
import dgpejbf.portal.domain.secundaria.Ciudad;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    
    // Este método te permite obtener todas las ciudades filtrando por el ID del país
    List<Ciudad> findByPaisId(Integer paisId);
}
