package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.GiftCertificateService;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class GiftCertificateController {
    private final Gson gson;
    private final GiftCertificateService<GiftCertificateDTO> giftCertificateService;

    public GiftCertificateController(Gson gson, GiftCertificateService<GiftCertificateDTO> giftCertificateService) {
        this.gson = gson;
        this.giftCertificateService = giftCertificateService;
    }
    @GetMapping("/hello")
    public String getHello(){
        return "Hello!";
    }

    @GetMapping("/certificates")
    public ResponseEntity<String> getCertificates(){
        return ResponseEntity.ok(gson.toJson(giftCertificateService.getAll()));
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<String> getCertificateById(@PathVariable long id){
        log.info("> > > { Get Request | Get Gift Certificate By Id }");
        return ResponseEntity.ok(gson.toJson(giftCertificateService.getById(id)));
    }

    @PostMapping("/tag")
    public ResponseEntity<String> getByTag(@RequestBody @Valid TagDTO tagDTO, BindingResult result){
        log.info("> > > { Post Request | Get Gift Certificate By Tag");
        if (result.hasErrors()){
            System.out.println("Problems with constraint");
            log.error("Something went wrong");
        }
        List<GiftCertificateDTO> byTag = giftCertificateService.getByTag(tagDTO);
        System.out.println(giftCertificateService.getByTag(tagDTO));
        return ResponseEntity.ok(gson.toJson(byTag));
    }


}
