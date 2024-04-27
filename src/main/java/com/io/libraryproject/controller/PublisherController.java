package com.io.libraryproject.controller;

import com.io.libraryproject.dto.PublisherDTO;
import com.io.libraryproject.dto.request.PublisherRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
    @PostMapping
    public ResponseEntity<LbResponse> savePublisher(@Valid @RequestBody PublisherRequest publisherRequest) {
        publisherService.savePublisher(publisherRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.PUBLISHER_SAVED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(lbResponse, HttpStatus.CREATED);
    }

    @GetMapping("/visitors/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable Long id) {
        PublisherDTO publisherDTO = publisherService.getPublisherById(id);

        return ResponseEntity.ok(publisherDTO);
    }
    @GetMapping("/visitors/page")
    public ResponseEntity<Page<PublisherDTO>> getAllPublisherByPage(@RequestParam("page") int page,
                                                                    @RequestParam("size") int size,
                                                                    @RequestParam("sort") String prop,
                                                                    @RequestParam(value = "direction", required = false,
                                                                    defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<PublisherDTO> allPublisherByPage = publisherService.getAllPublisherByPage(pageable);

        return  ResponseEntity.ok(allPublisherByPage);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LbResponse> updatePublisher(@PathVariable Long id,
                                                      @Valid @RequestBody PublisherRequest publisherRequest){
        publisherService.updatePublisher(id,publisherRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.PUBLISHER_UPDATED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(lbResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<LbResponse> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);

        LbResponse lbResponse = new LbResponse(ResponseMessage.PUBLISHER_DELETED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(lbResponse);
    }
}
