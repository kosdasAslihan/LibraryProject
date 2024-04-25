package com.io.libraryproject.service;

import com.io.libraryproject.dto.AuthorDTO;
import com.io.libraryproject.dto.request.AuthorRequest;
import com.io.libraryproject.entity.Author;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.AuthorMapper;
import com.io.libraryproject.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private  final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public void saveAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());

        authorRepository.save(author);
    }

    public List<AuthorDTO> getAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        return authorMapper.authorMap(authors);
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_EXCEPTION, id)));
        return authorMapper.authortoAuthorDTO(author);
    }

    public Page<AuthorDTO> getAllAuthorByPage(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);

        return authors.map(authorMapper::authortoAuthorDTO);
    }

    public void updateAuthor(Long id, AuthorRequest authorRequest) {
        Author author = authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_EXCEPTION)));
        author.setName(authorRequest.getName());
        authorRepository.save(author);
    }

    public void deleteAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_EXCEPTION)));
        authorRepository.delete(author);
    }
}
