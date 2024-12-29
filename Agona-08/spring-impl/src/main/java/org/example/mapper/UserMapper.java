package org.example.mapper;

import org.example.dto.request.UserRequest;
import org.example.dto.response.UserResponse;
import org.example.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

    @Mapper(componentModel = "spring", uses = RoleMapper.class)
    public interface UserMapper {

        @Mapping(target = "uuid", ignore = true)
        @Mapping(target = "roles", source = "roles")
        UserEntity toEntity(UserRequest userRequest);

        @Mapping(target = "roles", source = "roles")
        UserResponse toResponse(UserEntity userEntity);
    }

