package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.TagService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/tags")
public class TagController {
    private final TagService<TagDTO> tagService;

    public TagController(TagService<TagDTO> tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResMessage<List<TagDTO>> getTags() {
        return new ResMessage<>(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResMessage<TagDTO> getTagById(@PathVariable long id) {
        return new ResMessage<>(tagService.getById(id));
    }


    @PostMapping
    public ResMessage<Object> createTag(@RequestBody @Valid TagDTO tagDTO, BindingResult result) {
        log.info("Requesting create . . . ");
        if (result.hasErrors()) {
            log.error("Something wrong");
            return new ResMessage<>(HttpStatus.BAD_REQUEST, "Could not be created");
        }
        tagService.save(tagDTO);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }

    @DeleteMapping("/{id}")
    public ResMessage<Object> deleteTagById(@PathVariable long id) {
        tagService.delete(id);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }
}
