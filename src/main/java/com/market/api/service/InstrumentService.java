package com.market.api.service;

import com.market.api.dto.InstrumentCreateRequest;
import com.market.api.dto.InstrumentDTO;
import com.market.api.dto.InstrumentUpdateRequest;
import com.market.api.mapper.InstrumentMapper;
import com.market.api.model.Instrument;
import com.market.api.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer for Instrument business logic
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final InstrumentMapper instrumentMapper;

    public List<InstrumentDTO> getAllInstruments() {
        log.debug("Fetching all instruments");
        return instrumentRepository.findAll().stream()
                .map(instrumentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public InstrumentDTO getInstrumentById(UUID id) {
        log.debug("Fetching instrument by id: {}", id);
        Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument not found with id: " + id));
        return instrumentMapper.toDTO(instrument);
    }

    public InstrumentDTO createInstrument(InstrumentCreateRequest request) {
        log.info("Creating new instrument: {}", request.getSymbol());

        // Validate price is positive
        if (request.getPrice().signum() <= 0) {
            throw new InvalidRequestException("Price must be positive");
        }

        Instrument instrument = instrumentMapper.toEntity(request);
        instrument.setId(UUID.randomUUID());

        Instrument saved = instrumentRepository.save(instrument);
        return instrumentMapper.toDTO(saved);
    }

    public InstrumentDTO updateInstrument(UUID id, InstrumentUpdateRequest request) {
        log.info("Updating instrument: {}", id);

        Instrument existing = instrumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument not found with id: " + id));

        // Validate price if provided
        if (request.getPrice() != null && request.getPrice().signum() <= 0) {
            throw new InvalidRequestException("Price must be positive");
        }

        instrumentMapper.updateEntityFromDTO(request, existing);
        Instrument updated = instrumentRepository.save(existing);

        return instrumentMapper.toDTO(updated);
    }

    public void deleteInstrument(UUID id) {
        log.info("Deleting instrument: {}", id);

        if (!instrumentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Instrument not found with id: " + id);
        }

        instrumentRepository.deleteById(id);
    }
}
