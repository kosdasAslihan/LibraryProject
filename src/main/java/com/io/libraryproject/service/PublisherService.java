package com.io.libraryproject.service;

import com.io.libraryproject.dto.PublisherDTO;
import com.io.libraryproject.dto.request.PublisherRequest;
import com.io.libraryproject.entity.Author;
import com.io.libraryproject.entity.Publisher;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.PublisherMapper;
import com.io.libraryproject.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public PublisherService(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    public void savePublisher(PublisherRequest publisherRequest) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherRequest.getName());

        publisherRepository.save(publisher);
    }

    public PublisherDTO getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_EXCEPTION, id)));
        return publisherMapper.publisherToPublisherDTO(publisher);
    }


    public Page<PublisherDTO> getAllPublisherByPage(Pageable pageable) {
        Page<Publisher> publishers = publisherRepository.findAll(pageable);

        return publishers.map(publisherMapper::publisherToPublisherDTO);
    }

    public void updatePublisher(Long id, PublisherRequest publisherRequest) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_EXCEPTION)));
        publisher.setName(publisherRequest.getName());
        publisherRepository.save(publisher);
    }

    public void deletePublisher(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_EXCEPTION)));
        publisherRepository.delete(publisher);
    }
}
