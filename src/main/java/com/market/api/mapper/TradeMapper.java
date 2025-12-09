package com.market.api.mapper;

import com.market.api.dto.TradeCreateRequest;
import com.market.api.dto.TradeDTO;
import com.market.api.model.Trade;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for Trade entity and DTOs
 */
@Mapper(componentModel = "spring")
public interface TradeMapper {

    TradeDTO toDTO(Trade trade);

    Trade toEntity(TradeCreateRequest request);
}
