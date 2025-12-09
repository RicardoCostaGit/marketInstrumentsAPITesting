package com.market.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain model representing a trade transaction
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    private UUID id;
    private UUID userId;
    private UUID instrumentId;
    private Integer quantity;
    private TradeSide side;
    private LocalDateTime timestamp;
}
