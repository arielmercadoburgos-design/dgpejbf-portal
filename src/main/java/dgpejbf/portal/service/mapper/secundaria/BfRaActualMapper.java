package dgpejbf.portal.service.mapper.secundaria;

import dgpejbf.portal.domain.secundaria.BfRaActual;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BfRaActualMapper {

    BfRaActualDTO toDto(BfRaActual entity);

    BfRaActual toEntity(BfRaActualDTO dto);
}
