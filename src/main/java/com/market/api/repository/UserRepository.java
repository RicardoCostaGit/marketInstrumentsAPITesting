package com.market.api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.api.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for managing User data in memory
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final ObjectMapper objectMapper;
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("mockdata/users.json");
            List<User> userList = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<User>>() {
                    });

            userList.forEach(user -> users.put(user.getId(), user));
            log.info("Loaded {} users from JSON", users.size());
        } catch (IOException e) {
            log.error("Failed to load users data", e);
            throw new RuntimeException("Failed to load users data", e);
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        users.put(user.getId(), user);
        log.info("Saved user: {}", user.getId());
        return user;
    }

    public boolean existsById(UUID id) {
        return users.containsKey(id);
    }
}
