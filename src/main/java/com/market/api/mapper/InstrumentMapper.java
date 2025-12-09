package com.market.api.mapper;

import com.market.api.dto.InstrumentCreateRequest;
import com.market.api.dto.InstrumentDTO;
import com.market.api.dto.InstrumentUpdateRequest;
import com.market.api.model.Instrument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for Instrument entity and DTOs
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InstrumentMapper {

    InstrumentDTO toDTO(Instrument instrument);

    Instrument toEntity(InstrumentCreateRequest request);

    void updateEntityFromDTO(InstrumentUpdateRequest request, @MappingTarget Instrument instrument);
}
