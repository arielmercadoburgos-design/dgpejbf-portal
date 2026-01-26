package dgpejbf.portal.service.mapper.secundaria;

import dgpejbf.portal.domain.secundaria.PejRaActualSocios;
import dgpejbf.portal.service.dto.PejRaActualSociosDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PejRaActualSociosMapper {

    PejRaActualSociosDTO toDto(PejRaActualSocios entity);

    PejRaActualSocios toEntity(PejRaActualSociosDTO dto);
}

