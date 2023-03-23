package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.GiftCertificateService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResMessage getCertificates() {
        return new ResMessage(200, "Ok", giftCertificateService.getAll());
    }

    @GetMapping("/{id}")
    public ResMessage getCertificateById(@PathVariable long id) {
        log.info("> > > { Get Request | Get Gift Certificate By Id }");
        return new ResMessage(200, "Ok", giftCertificateService.getById(id));
    }


    @PostMapping
    public ResponseEntity<String> createGiftCertificate(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO, BindingResult result) {
        log.info("> > > { Post Request | Create a new GiftCertificate }");
        if (result.hasErrors()) {
            System.out.println("Problems with constraint!");
            log.error("Something went wrong with constraints!");
        }

        giftCertificateService.save(giftCertificateDTO);
        return ResponseEntity.ok("Gift Certificate successfully created!");
    }

    @PatchMapping
    public ResMessage update(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO, BindingResult result) {
        log.info("> > > {Patch Request | Update an existing GiftCertificate }");
        if (result.hasErrors()) {
            System.out.println("Problems with constraint!");
            log.error("Something is wrong!");
            return new ResMessage(101, "no update");
        }
        giftCertificateService.update(giftCertificateDTO);
        return new ResMessage(200, "Success");
    }

    @DeleteMapping("/{id}")
    public ResMessage deleteById(@PathVariable long id) {
        giftCertificateService.delete(id);
        return new ResMessage(200, "Success");
    }

    @PutMapping
    public ResMessage getGiftCertificatesWithTags(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "tag", required = false) String tagName,
            @RequestParam(name = "sort", defaultValue = "name_asc") String sort) {
        List<GiftCertificateDTO> giftCertificateWithTags = giftCertificateService.getGiftCertificateWithTags(name, tagName, description, sort);

        return new ResMessage(200, "Ok", giftCertificateWithTags);
    }

}
