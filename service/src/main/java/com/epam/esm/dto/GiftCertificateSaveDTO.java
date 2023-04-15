package com.epam.esm.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateSaveDTO {

    long id;

    @NotNull(message = "name should not be null")
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 25, message = "Name has to be between 3 and 25 characters")
    String name;

    @NotNull(message = "description should not be null")
    @NotEmpty(message = "description should not be empty")
    @Size(min = 5, max = 120, message = "Description must be between 8 and 80 characters")
    String description;

    @NotNull(message = "description should not be null")
    @NotEmpty(message = "description should not be empty")
    @Size(min = 2, max = 10, message = "Price must be between 2 and 8 digits")
    String price;

    @NotNull(message = "description should not be null")
    @NotEmpty(message = "description should not be empty")
    @Size(min = 1, max = 60, message = "duration should be between 1 and 60 characters")
    String duration;
    String createDate;
    String lastUpdateDate;

    @Valid
    List<TagDTO> tags;
}
