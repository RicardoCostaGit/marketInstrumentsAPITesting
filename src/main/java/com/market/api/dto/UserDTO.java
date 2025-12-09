package com.market.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for User responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User details")
public class UserDTO {

    @Schema(description = "Unique identifier", example = "22222222-aaaa-bbbb-cccc-000000000001")
    private UUID id;

    @Schema(description = "Username", example = "john_trader")
    private String username;

    @Schema(description = "Country code", example = "US")
    private String country;

    @Schema(description = "Account balance", example = "50000.00")
    private BigDecimal balance;
}
