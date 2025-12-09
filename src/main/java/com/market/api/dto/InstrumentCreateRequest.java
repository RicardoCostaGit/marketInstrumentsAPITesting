package com.market.api.dto;

import com.market.api.model.InstrumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for creating new instruments
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create a new instrument")
public class InstrumentCreateRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "Instrument name", example = "Litecoin", required = true)
    private String name;

    @NotNull(message = "Type is required")
    @Schema(description = "Instrument type", example = "CRYPTO", required = true)
    private InstrumentType type;

    @NotBlank(message = "Symbol is required")
    @Schema(description = "Trading symbol", example = "LTCUSD", required = true)
    private String symbol;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Schema(description = "Current price", example = "85.50", required = true)
    private BigDecimal price;
}
