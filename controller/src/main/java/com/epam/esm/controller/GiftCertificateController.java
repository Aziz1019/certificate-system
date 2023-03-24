package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.GiftCertificateService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateService<GiftCertificateDTO> giftCertificateService;

    public GiftCertificateController(GiftCertificateService<GiftCertificateDTO> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public ResMessage<List<GiftCertificateDTO>> getCertificates() {
        return new ResMessage<>(giftCertificateService.getAll());
    }

    @GetMapping("/{id}")
    public ResMessage<GiftCertificateDTO> getCertificateById(@PathVariable long id) {
        log.info("> > > { Get Request | Get Gift Certificate By Id }");
        return new ResMessage<>(giftCertificateService.getById(id));
    }


    @PostMapping
    public ResMessage<Object> createGiftCertificate(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO, BindingResult result) {
        log.info("> > > { Post Request | Create a new GiftCertificate }");
        if (result.hasErrors()) {
            log.error("Something went wrong, check validation");
            List<String> violations = new ArrayList<>();
            result.getFieldErrors().forEach(fieldError
                    -> violations.add(fieldError.getDefaultMessage()));
            return new ResMessage<>(HttpStatus.BAD_REQUEST, "Could not create", violations);
        }
        giftCertificateService.save(giftCertificateDTO);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }

    @PatchMapping
    public ResMessage<Object> update(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO, BindingResult result) {
        log.info("> > > { Patch Request | Update an existing GiftCertificate }");
        if (result.hasErrors()) {
            log.error("Something went wrong, check validation");
            List<String> violations = new ArrayList<>();
            result.getFieldErrors().forEach(fieldError
                    -> violations.add(fieldError.getDefaultMessage()));
            return new ResMessage<>(HttpStatus.NOT_MODIFIED, "Could not update", violations);
        }
        giftCertificateService.update(giftCertificateDTO);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }

    @DeleteMapping("/{id}")
    public ResMessage<Object> deleteById(@PathVariable long id) {
        giftCertificateService.delete(id);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }

    @PutMapping
    public ResMessage<List<GiftCertificateDTO>> getGiftCertificatesWithTags(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "tag", required = false) String tagName,
            @RequestParam(name = "sort", defaultValue = "name_asc") String sort) {

        return new ResMessage<>(giftCertificateService.getGiftCertificateWithTags(name, tagName, description, sort));
    }

}
