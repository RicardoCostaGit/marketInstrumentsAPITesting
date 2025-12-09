package com.market.api.controller;

import com.market.api.dto.ApiResponse;
import com.market.api.dto.InstrumentCreateRequest;
import com.market.api.dto.InstrumentDTO;
import com.market.api.dto.InstrumentUpdateRequest;
import com.market.api.service.InstrumentService;
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
 * REST Controller for Instrument endpoints
 */
@RestController
@RequestMapping("/api/instruments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Instruments", description = "Financial instruments management API")
public class InstrumentController {

    private final InstrumentService instrumentService;

    @GetMapping
    @Operation(summary = "Get all instruments", description = "Retrieve a list of all available financial instruments")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved instruments", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<List<InstrumentDTO>>> getAllInstruments() {
        List<InstrumentDTO> instruments = instrumentService.getAllInstruments();
        return ResponseEntity.ok(ApiResponse.success(instruments));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get instrument by ID", description = "Retrieve a specific instrument by its UUID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved instrument", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Instrument not found")
    })
    public ResponseEntity<ApiResponse<InstrumentDTO>> getInstrumentById(@PathVariable UUID id) {
        InstrumentDTO instrument = instrumentService.getInstrumentById(id);
        return ResponseEntity.ok(ApiResponse.success(instrument));
    }

    @PostMapping
    @Operation(summary = "Create new instrument", description = "Create a new financial instrument")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Instrument created successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<ApiResponse<InstrumentDTO>> createInstrument(
            @Valid @RequestBody InstrumentCreateRequest request) {
        InstrumentDTO created = instrumentService.createInstrument(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Instrument created successfully", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update instrument", description = "Update an existing instrument")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Instrument updated successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Instrument not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<ApiResponse<InstrumentDTO>> updateInstrument(
            @PathVariable UUID id,
            @Valid @RequestBody InstrumentUpdateRequest request) {
        InstrumentDTO updated = instrumentService.updateInstrument(id, request);
        return ResponseEntity.ok(ApiResponse.success("Instrument updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete instrument", description = "Delete an instrument by its UUID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Instrument deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Instrument not found")
    })
    public ResponseEntity<ApiResponse<Void>> deleteInstrument(@PathVariable UUID id) {
        instrumentService.deleteInstrument(id);
        return ResponseEntity.ok(ApiResponse.success("Instrument deleted successfully", null));
    }
}
