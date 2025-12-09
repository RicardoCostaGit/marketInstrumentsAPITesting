package com.market.api.controller;

import com.market.api.dto.ApiResponse;
import com.market.api.dto.TradeCreateRequest;
import com.market.api.dto.TradeDTO;
import com.market.api.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Trade endpoints
 */
@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
@Validated
@Tag(name = "Trades", description = "Trade management API")
public class TradeController {

    private final TradeService tradeService;

    @GetMapping
    @Operation(summary = "Get all trades", description = "Retrieve a list of all trades")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved trades", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<List<TradeDTO>>> getAllTrades() {
        List<TradeDTO> trades = tradeService.getAllTrades();
        return ResponseEntity.ok(ApiResponse.success(trades));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get trade by ID", description = "Retrieve a specific trade by its UUID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved trade", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Trade not found")
    })
    public ResponseEntity<ApiResponse<TradeDTO>> getTradeById(@PathVariable UUID id) {
        TradeDTO trade = tradeService.getTradeById(id);
        return ResponseEntity.ok(ApiResponse.success(trade));
    }

    @PostMapping
    @Operation(summary = "Create new trade", description = "Create a new trade transaction")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Trade created successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<ApiResponse<TradeDTO>> createTrade(
            @Valid @RequestBody TradeCreateRequest request) {
        TradeDTO created = tradeService.createTrade(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Trade created successfully", created));
    }
}
