package com.epam.esm.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Tag implements Comparable<Tag> {
    private Long id;
    private String name;

    @Override
    public int compareTo(Tag tag) {
        return this.getName().compareTo(tag.getName());
    }
}
