package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.Validator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class that handles HTTP requests related to Tag resources.
 */
@RestController
@Slf4j
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Handles GET requests to retrieve all Tag resources.
     *
     * @return a ResMessage containing the list of all TagDTO resources and a success message.
     */
    @GetMapping
    public ResMessage<List<TagDTO>> getTags() {
        return new ResMessage<>(tagService.getAll());
    }

    /**
     * Handles GET requests to retrieve a specific Tag resource by ID.
     *
     * @param id the ID of the Tag resource to retrieve.
     * @return a ResMessage containing the TagDTO resource with the specified ID and a success message.
     */
    @GetMapping("/{id}")
    public ResMessage<TagDTO> getTagById(@PathVariable long id) {
        return new ResMessage<>(tagService.getById(id));
    }

    /**
     * Handles POST requests to create a new Tag resource.
     *
     * @param tagDTO the TagDTO resource to create.
     * @param result the result of the validation performed on the TagDTO resource.
     * @return a ResMessage indicating whether the TagDTO resource was successfully created or not, along with any error messages.
     */
    @PostMapping
    public ResponseEntity<ResMessage<Object>> createTag(@RequestBody @Valid TagDTO tagDTO, BindingResult result) throws ServiceException {
        log.info("Requesting create . . . ");
        Validator.validateForSave(result);
        return new ResponseEntity<>(new ResMessage<>(HttpStatus.OK, "Success", "New Tag ID:" + tagService.save(tagDTO)), HttpStatus.CREATED);
    }

    /**
     * Handles DELETE requests to delete a specific Tag resource by ID.
     *
     * @param id the ID of the Tag resource to delete.
     * @return a ResMessage indicating whether the TagDTO resource was successfully deleted or not.
     */
    @DeleteMapping("/{id}")
    public ResMessage<Object> deleteTagById(@PathVariable long id) throws ServiceException {
        tagService.delete(id);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }
}
