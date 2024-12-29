package org.example.mapper;

import org.example.dto.request.RoleRequest;
import org.example.dto.response.RoleResponse;
import org.example.model.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "uuid", ignore = true)
    RoleEntity toEntity(RoleRequest roleRequest);

    RoleResponse toResponse(RoleEntity roleEntity);
}
