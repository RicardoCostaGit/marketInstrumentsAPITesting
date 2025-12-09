package com.market.api.dto;

import com.market.api.model.TradeSide;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for creating new trades
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create a new trade")
public class TradeCreateRequest {

    @NotNull(message = "User ID is required")
    @Schema(description = "User ID", example = "22222222-aaaa-bbbb-cccc-000000000001", required = true)
    private UUID userId;

    @NotNull(message = "Instrument ID is required")
    @Schema(description = "Instrument ID", example = "11111111-aaaa-bbbb-cccc-000000000002", required = true)
    private UUID instrumentId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Schema(description = "Quantity", example = "5", required = true)
    private Integer quantity;

    @NotNull(message = "Side is required")
    @Schema(description = "Trade side", example = "BUY", required = true)
    private TradeSide side;
}
