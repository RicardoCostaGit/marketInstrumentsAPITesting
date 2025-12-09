package com.market.api.dto;

import com.market.api.model.InstrumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for updating existing instruments
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update an instrument")
public class InstrumentUpdateRequest {

    @Schema(description = "Instrument name", example = "Bitcoin")
    private String name;

    @Schema(description = "Instrument type", example = "CRYPTO")
    private InstrumentType type;

    @Schema(description = "Trading symbol", example = "BTCUSD")
    private String symbol;

    @Positive(message = "Price must be positive")
    @Schema(description = "Current price", example = "45000.00")
    private BigDecimal price;
}
