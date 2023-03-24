package com.epam.esm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDTO {
    long id;
    @NotNull(message = "Name must not be null")
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 25, message = "Name has to be between 3 and 25 characters")
    String name;
    @NotNull(message = "Description must not be null")
    @NotEmpty(message = "Description must not be empty")
    @Size(min = 5, max = 120, message = "Description must be between 8 and 80 characters")
    String description;
    @NotNull(message = "Price must not be null")
    @NotEmpty(message = "Price must not be empty")
    @Size(min = 2, max = 10, message = "Price must be between 2 and 8 digits")
    String price;
    @NotNull(message = "Duration must not be null")
    @NotEmpty(message = "Duration must not be empty")
    @Size(min = 1, max = 60, message = "duration should be between 1 and 60 characters")
    String duration;
    String createDate;
    String lastUpdateDate;
    @NotNull(message = "Tags must not be null")
    List<TagDTO> tags;
}
