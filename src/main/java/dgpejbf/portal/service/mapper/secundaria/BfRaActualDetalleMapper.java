package dgpejbf.portal.service.mapper.secundaria;

import dgpejbf.portal.domain.secundaria.BfRaActualDetalle;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDetalleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BfRaActualDetalleMapper {
    BfRaActualDetalleDTO toDto(BfRaActualDetalle entity);
    BfRaActualDetalle toEntity(BfRaActualDetalleDTO dto);
}
