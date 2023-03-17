package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/tags")
public class TagController {
    private final Gson gson;
    private final TagService<TagDTO> tagService;

    public TagController(Gson gson, TagService<TagDTO> tagService) {
        this.gson = gson;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<String> getTags(){
        List<TagDTO> all = tagService.getAll();
        return ResponseEntity.ok(gson.toJson(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTagById(@PathVariable long id){
        TagDTO tagById = tagService.getById(id);
        return ResponseEntity.ok(gson.toJson(tagById));
    }


    @PostMapping
    public ResponseEntity<String> createTag(@RequestBody @Valid TagDTO tagDTO, BindingResult result){
        log.info("Requesting create . . . ");
        if (result.hasErrors()){
            System.out.println("problems with constraints");
            log.error("Something wrong");
        }
        tagService.save(tagDTO);
        return ResponseEntity.ok("tag saved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTagById(@PathVariable long id){
        tagService.delete(id);
        return ResponseEntity.ok("Tag Deleted Successfully");
    }






}
