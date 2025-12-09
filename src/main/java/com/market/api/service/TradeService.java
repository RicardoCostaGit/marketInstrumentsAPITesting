package com.market.api.service;

import com.market.api.dto.TradeCreateRequest;
import com.market.api.dto.TradeDTO;
import com.market.api.mapper.TradeMapper;
import com.market.api.model.Trade;
import com.market.api.repository.InstrumentRepository;
import com.market.api.repository.TradeRepository;
import com.market.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer for Trade business logic
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;
    private final InstrumentRepository instrumentRepository;
    private final TradeMapper tradeMapper;

    public List<TradeDTO> getAllTrades() {
        log.debug("Fetching all trades");
        return tradeRepository.findAll().stream()
                .map(tradeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TradeDTO getTradeById(UUID id) {
        log.debug("Fetching trade by id: {}", id);
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not found with id: " + id));
        return tradeMapper.toDTO(trade);
    }

    public TradeDTO createTrade(TradeCreateRequest request) {
        log.info("Creating new trade for user: {} and instrument: {}",
                request.getUserId(), request.getInstrumentId());

        // Validate user exists
        if (!userRepository.existsById(request.getUserId())) {
            throw new InvalidRequestException("User not found with id: " + request.getUserId());
        }

        // Validate instrument exists
        if (!instrumentRepository.existsById(request.getInstrumentId())) {
            throw new InvalidRequestException("Instrument not found with id: " + request.getInstrumentId());
        }

        // Validate quantity is positive
        if (request.getQuantity() <= 0) {
            throw new InvalidRequestException("Quantity must be positive");
        }

        Trade trade = tradeMapper.toEntity(request);
        trade.setId(UUID.randomUUID());
        trade.setTimestamp(LocalDateTime.now());

        Trade saved = tradeRepository.save(trade);
        return tradeMapper.toDTO(saved);
    }
}
