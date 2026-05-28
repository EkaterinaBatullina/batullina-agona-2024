package org.example.mapper;

import org.example.dto.request.UserRequest;
import org.example.dto.response.UserResponse;
import org.example.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserRequest userRequest);

    UserResponse toResponse(UserEntity userEntity);
}

