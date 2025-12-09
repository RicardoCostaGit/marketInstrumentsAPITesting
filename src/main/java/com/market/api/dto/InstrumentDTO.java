package com.market.api.dto;

import com.market.api.model.InstrumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for Instrument responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Instrument details")
public class InstrumentDTO {

    @Schema(description = "Unique identifier", example = "11111111-aaaa-bbbb-cccc-000000000001")
    private UUID id;

    @Schema(description = "Instrument name", example = "Bitcoin")
    private String name;

    @Schema(description = "Instrument type", example = "CRYPTO")
    private InstrumentType type;

    @Schema(description = "Trading symbol", example = "BTCUSD")
    private String symbol;

    @Schema(description = "Current price", example = "43210.55")
    private BigDecimal price;
}
