package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.BfRaActualDetalle;
import dgpejbf.portal.repository.secundaria.BfRaActualDetalleRepository;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDetalleDTO;
import dgpejbf.portal.service.mapper.secundaria.BfRaActualDetalleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BfRaActualDetalleService {

    private final BfRaActualDetalleRepository detalleRepository;
    private final BfRaActualDetalleMapper detalleMapper;

    public BfRaActualDetalleService(BfRaActualDetalleRepository detalleRepository,
                                   BfRaActualDetalleMapper detalleMapper) {
        this.detalleRepository = detalleRepository;
        this.detalleMapper = detalleMapper;
    }

    /**
     * Buscar detalles por filtros opcionales y paginación
     */
    public Page<BfRaActualDetalleDTO> buscar(Integer ruc, String nombre, Pageable pageable) {
        Page<BfRaActualDetalle> page = detalleRepository.buscar(ruc, nombre, pageable);
        return page.map(detalleMapper::toDto);
    }

    /**
     * Traer todos los detalles de una empresa (ruc) para export
     */
    public List<BfRaActualDetalleDTO> findAllByRuc(Integer ruc) {
        return detalleRepository.findByRuc(ruc)
                .stream()
                .map(detalleMapper::toDto)
                .toList();
    }
}
