package org.example.mapper;

import org.example.dto.request.BookRequest;
import org.example.dto.response.BookResponse;
import org.example.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "author", source = "authorId")
    BookEntity toEntity(BookRequest bookRequest);

    @Mapping(target = "authorId", source = "author.uuid")
    BookResponse toResponse(BookEntity bookEntity);
}