package com.market.api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.api.model.Instrument;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for managing Instrument data in memory
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class InstrumentRepository {

    private final ObjectMapper objectMapper;
    private final Map<UUID, Instrument> instruments = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("mockdata/instruments.json");
            List<Instrument> instrumentList = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<Instrument>>() {
                    });

            instrumentList.forEach(instrument -> instruments.put(instrument.getId(), instrument));
            log.info("Loaded {} instruments from JSON", instruments.size());
        } catch (IOException e) {
            log.error("Failed to load instruments data", e);
            throw new RuntimeException("Failed to load instruments data", e);
        }
    }

    public List<Instrument> findAll() {
        return new ArrayList<>(instruments.values());
    }

    public Optional<Instrument> findById(UUID id) {
        return Optional.ofNullable(instruments.get(id));
    }

    public Instrument save(Instrument instrument) {
        if (instrument.getId() == null) {
            instrument.setId(UUID.randomUUID());
        }
        instruments.put(instrument.getId(), instrument);
        log.info("Saved instrument: {}", instrument.getId());
        return instrument;
    }

    public void deleteById(UUID id) {
        instruments.remove(id);
        log.info("Deleted instrument: {}", id);
    }

    public boolean existsById(UUID id) {
        return instruments.containsKey(id);
    }
}
