package com.epam.esm.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    long id;
    @NotNull(message = "Name must not be null")
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 15, message = "Name has to be between 3 and 15 characters")
    String name;
}
