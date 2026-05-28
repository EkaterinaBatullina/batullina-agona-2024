package org.example.mapper;

import org.example.dto.request.AuthorRequest;
import org.example.dto.response.AuthorResponse;
import org.example.model.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookMapper.class, AuthorProfileMapper.class})
public interface AuthorMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "profile", source = "profile")
    AuthorEntity toEntity(AuthorRequest authorRequest);

    @Mapping(target = "books", source = "books")
    @Mapping(target = "profile", source = "profile")
    AuthorResponse toResponse(AuthorEntity authorEntity);
}