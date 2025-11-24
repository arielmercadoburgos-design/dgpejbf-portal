package dgpejbf.portal.service.mapper.secundaria;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import dgpejbf.portal.service.dto.PejRaActualDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PejRaActualMapper {
    PejRaActualDTO toDto(PejRaActual entity);

    PejRaActual toEntity(PejRaActualDTO dto);

    default LocalDateTime map(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}
