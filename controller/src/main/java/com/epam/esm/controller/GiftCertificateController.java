package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
        System.out.println(gson.toJson(giftCertificateService.getAll()));
        return ResponseEntity.ok(gson.toJson(giftCertificateService.getAll()));
    }
}
