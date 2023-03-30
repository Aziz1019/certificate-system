package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long duration;
    private String createDate;
    private String lastUpdateDate;
    private Set<Tag> tags;

    public GiftCertificate(String name, String description, Double price, Long duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}

