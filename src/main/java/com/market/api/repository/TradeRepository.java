package com.market.api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.api.model.Trade;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for managing Trade data in memory
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TradeRepository {

    private final ObjectMapper objectMapper;
    private final Map<UUID, Trade> trades = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("mockdata/trades.json");
            List<Trade> tradeList = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<Trade>>() {
                    });

            tradeList.forEach(trade -> trades.put(trade.getId(), trade));
            log.info("Loaded {} trades from JSON", trades.size());
        } catch (IOException e) {
            log.error("Failed to load trades data", e);
            throw new RuntimeException("Failed to load trades data", e);
        }
    }

    public List<Trade> findAll() {
        return new ArrayList<>(trades.values());
    }

    public Optional<Trade> findById(UUID id) {
        return Optional.ofNullable(trades.get(id));
    }

    public Trade save(Trade trade) {
        if (trade.getId() == null) {
            trade.setId(UUID.randomUUID());
        }
        trades.put(trade.getId(), trade);
        log.info("Saved trade: {}", trade.getId());
        return trade;
    }

    public boolean existsById(UUID id) {
        return trades.containsKey(id);
    }
}
