package dgpejbf.portal.service;

import dgpejbf.portal.domain.secundaria.BfRaActual;
import dgpejbf.portal.repository.secundaria.BfRaActualRepository;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDTO;
import dgpejbf.portal.service.mapper.secundaria.BfRaActualMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BfRaActualService {

    private final BfRaActualRepository bfRaActualRepository;
    private final BfRaActualMapper bfRaActualMapper;

    public BfRaActualService(BfRaActualRepository bfRaActualRepository,
                             BfRaActualMapper bfRaActualMapper) {
        this.bfRaActualRepository = bfRaActualRepository;
        this.bfRaActualMapper = bfRaActualMapper;
    }

    /**
     * Buscar Beneficiarios Finales con filtros opcionales y paginación
     */
    public Page<BfRaActualDTO> buscar(Integer ruc, String razonSocial, String tipoComunicacion, Pageable pageable) {
        Page<BfRaActual> page = bfRaActualRepository.buscar(ruc, razonSocial, tipoComunicacion, pageable);
        return page.map(bfRaActualMapper::toDto);
    }

    /**
     * Traer un Beneficiario Final por ID (opcional)
     */
    public BfRaActualDTO findOne(Integer id) {
        return bfRaActualRepository.findById(id)
                .map(bfRaActualMapper::toDto)
                .orElse(null);
    }

    /**
     * Traer todos los Beneficiarios Finales (por ruc) para export
     */
    public java.util.List<BfRaActualDTO> findAllByRuc(Integer ruc) {
        return bfRaActualRepository.buscar(ruc, null, null, Pageable.unpaged())
                .map(bfRaActualMapper::toDto)
                .getContent();
    }
}
