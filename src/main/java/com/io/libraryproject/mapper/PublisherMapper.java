package com.io.libraryproject.mapper;

import com.io.libraryproject.dto.PublisherDTO;
import com.io.libraryproject.entity.Publisher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    List<PublisherDTO> publisherMap(List<Publisher> publishers);

    PublisherDTO publisherToPublisherDTO(Publisher publisher);

}
