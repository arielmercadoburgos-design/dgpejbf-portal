package dgpejbf.portal.repository.secundaria;
import dgpejbf.portal.domain.secundaria.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
    // Aquí puedes añadir búsquedas personalizadas más adelante, por ejemplo:
    List<Pais> findByNombre(String nombre);
}
