package com.market.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Domain model representing a financial instrument
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instrument {
    private UUID id;
    private String name;
    private InstrumentType type;
    private String symbol;
    private BigDecimal price;
}
