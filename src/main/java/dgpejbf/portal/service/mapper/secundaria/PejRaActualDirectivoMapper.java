package dgpejbf.portal.service.mapper.secundaria;

import dgpejbf.portal.domain.secundaria.PejRaActualDirectivo;
import dgpejbf.portal.service.dto.PejRaActualDirectivoDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PejRaActualDirectivoMapper {

    PejRaActualDirectivoDTO toDto(PejRaActualDirectivo entity);

    PejRaActualDirectivo ToEntity(PejRaActualDirectivoDTO dto);

    // Métodos para manejar la conversión de fechas entre Instant y LocalDateTime
    default LocalDateTime map(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}