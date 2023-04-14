package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.responseMessage.ResMessage;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.Validator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller class that handles HTTP requests related to Gift Certificate resources.
 */
@RestController
@Slf4j
@RequestMapping("/api/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Retrieves all gift certificates.
     *
     * @return a {@link ResMessage} with the list of gift certificates.
     */

    @GetMapping
    public ResMessage<List<GiftCertificateDTO>> getCertificates() {
        return new ResMessage<>(giftCertificateService.getAll());
    }

    /**
     * Retrieves a gift certificate by id.
     *
     * @param id the id of the gift certificate.
     * @return a {@link ResMessage} with the gift certificate.
     */

    @GetMapping("/{id}")
    public ResMessage<GiftCertificateDTO> getCertificateById(@PathVariable long id) {
        log.info("> > > { Get Request | Get Gift Certificate By Id }");
        return new ResMessage<>(giftCertificateService.getById(id));
    }


    /**
     * Creates a new gift certificate.
     *
     * @param giftCertificateDTO the gift certificate to be created.
     * @param result             the result of the binding.
     * @return a {@link ResMessage} with the status of the operation.
     */
    @PostMapping
    public ResMessage<Object> createGiftCertificate(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO, BindingResult result) throws ServiceException {
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

    /**
     * Updates an existing gift certificate.
     *
     * @param giftCertificateDTO the gift certificate to be updated.
     * @param result             the result of the binding.
     * @return a {@link ResMessage} with the status of the operation.
     */
    @PatchMapping
    public ResMessage<Object> update(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO, BindingResult result) throws  ServiceException {
        log.info("> > > { Patch Request | Update an existing GiftCertificate }");
        Validator.validate(giftCertificateDTO.getId(),result);
        giftCertificateService.update(giftCertificateDTO);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }

    /**
     * Deletes a gift certificate by id.
     *
     * @param id the id of the gift certificate to be deleted.
     * @return a {@link ResMessage} with the status of the operation.
     */

    @DeleteMapping("/{id}")
    public ResMessage<Object> deleteById(@PathVariable long id) throws ServiceException {
        giftCertificateService.delete(id);
        return new ResMessage<>(HttpStatus.OK, "Success");
    }

    /**
     * Retrieves a list of gift certificates with the specified name, tag name and/or description.
     */
    @PutMapping
    public ResMessage<List<GiftCertificateDTO>> getGiftCertificatesWithTags(@RequestParam Map<String, String> allParams) throws ServiceException {
        return new ResMessage<>(giftCertificateService.getGiftCertificateWithTags(allParams));
    }

}
