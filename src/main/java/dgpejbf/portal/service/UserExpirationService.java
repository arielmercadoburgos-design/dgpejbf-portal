package dgpejbf.portal.service;

import dgpejbf.portal.domain.User;
import dgpejbf.portal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserExpirationService {

    private final Logger log = LoggerFactory.getLogger(UserExpirationService.class);
    private final UserRepository userRepository;

    public UserExpirationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Se ejecuta todos los días a las 00:00:01 AM.
     * Busca usuarios activados cuya fecha de vencimiento ya pasó y los desactiva.
     */
    @Scheduled(cron = "1 0 0 * * ?") 
    public void deactivateExpiredUsers() {
        log.debug("Robot de vencimiento: Iniciando revisión diaria...");
        
        LocalDate hoy = LocalDate.now();
        
        // Buscamos solo los que están activos para no procesar de más
        List<User> usuariosActivos = userRepository.findAllByActivatedTrue();

        for (User user : usuariosActivos) {
            if (user.getFechaExpiracion() != null && user.getFechaExpiracion().isBefore(hoy)) {
                log.info("Robot: Desactivando al usuario {} porque su cuenta venció el {}", 
                    user.getLogin(), user.getFechaExpiracion());
                
                user.setActivated(false);
                userRepository.save(user);
                // Aquí el usuario ya quedó desactivado, igual que si hubieras pulsado el botón.
            }
        }
    }
}