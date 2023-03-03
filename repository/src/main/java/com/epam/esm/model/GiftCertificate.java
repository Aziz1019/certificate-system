package com.epam.esm.model;
import lombok.*;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long duration;
    private String createDate;
    private String lastUpdateDate;
    private Set<Tag> tags;


}

