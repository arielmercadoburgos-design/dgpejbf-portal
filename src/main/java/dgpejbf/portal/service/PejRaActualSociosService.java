package dgpejbf.portal.service;

import dgpejbf.portal.service.dto.PejRaActualSociosDTO;
import dgpejbf.portal.domain.secundaria.PejRaActualSocios;
import dgpejbf.portal.repository.secundaria.PejRaActualSociosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PejRaActualSociosService {

    private final PejRaActualSociosRepository sociosRepository;

    // Constructor para inyección de dependencias
    public PejRaActualSociosService(PejRaActualSociosRepository sociosRepository) {
        this.sociosRepository = sociosRepository;
    }

    /**
     * Obtiene los socios por RUC y los convierte a DTO.
     */
    @Transactional(readOnly = true)
    public List<PejRaActualSociosDTO> buscarPorRuc(Integer ruc) {
        return sociosRepository.findByRuc(ruc)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    /**
     * Método privado para pasar datos de la Entidad al DTO.
     */
    private PejRaActualSociosDTO convertirADTO(PejRaActualSocios socio) {
        PejRaActualSociosDTO dto = new PejRaActualSociosDTO();
        dto.setTablaId(socio.getTablaId());
        dto.setRuc(socio.getRuc());
        dto.setNombre(socio.getNombre());
        dto.setCedula(socio.getCedula());
        dto.setRucSocio(socio.getRucSocio());
        dto.setPorcentaje(socio.getPorcentaje());
        dto.setCantidadAcciones(socio.getCantidadAcciones());
        dto.setValorAcciones(socio.getValorAcciones());
        dto.setCantidadVotos(socio.getCantidadVotos());
        // Agrega aquí más campos si los necesitas en la grilla
        return dto;
    }
}