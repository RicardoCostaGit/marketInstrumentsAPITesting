package com.market.api.dto;

import com.market.api.model.TradeSide;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Trade responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Trade details")
public class TradeDTO {

    @Schema(description = "Unique identifier", example = "33333333-aaaa-bbbb-cccc-000000000001")
    private UUID id;

    @Schema(description = "User ID", example = "22222222-aaaa-bbbb-cccc-000000000001")
    private UUID userId;

    @Schema(description = "Instrument ID", example = "11111111-aaaa-bbbb-cccc-000000000002")
    private UUID instrumentId;

    @Schema(description = "Quantity", example = "10")
    private Integer quantity;

    @Schema(description = "Trade side", example = "BUY")
    private TradeSide side;

    @Schema(description = "Trade timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
}
