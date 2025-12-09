package com.market.api.mapper;

import com.market.api.dto.UserDTO;
import com.market.api.model.User;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for User entity and DTOs
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
