package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.TagService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/tags")
public class TagController {
    private final TagService<TagDTO> tagService;

    public TagController(TagService<TagDTO> tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResMessage getTags() {
        return new ResMessage(200, "ok", tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResMessage getTagById(@PathVariable long id) {
        return new ResMessage(200, "ok", tagService.getById(id));
    }


    @PostMapping
    public ResMessage createTag(@RequestBody @Valid TagDTO tagDTO, BindingResult result) {
        log.info("Requesting create . . . ");
        if (result.hasErrors()) {
            System.out.println("problems with constraints");
            log.error("Something wrong");
        }
        tagService.save(tagDTO);
        return new ResMessage(200, "Success");
    }

    @DeleteMapping("/{id}")
    public ResMessage deleteTagById(@PathVariable long id) {
        tagService.delete(id);
        return new ResMessage(200, "Success");
    }

}
