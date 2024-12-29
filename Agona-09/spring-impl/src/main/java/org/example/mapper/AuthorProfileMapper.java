package org.example.mapper;

import org.example.dto.request.AuthorProfileRequest;
import org.example.dto.response.AuthorProfileResponse;
import org.example.model.AuthorProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorProfileMapper {

    @Mapping(target = "uuid", ignore = true)
    AuthorProfileEntity toEntity(AuthorProfileRequest authorProfileRequest);

    AuthorProfileResponse toResponse(AuthorProfileEntity authorProfileEntity);
}
