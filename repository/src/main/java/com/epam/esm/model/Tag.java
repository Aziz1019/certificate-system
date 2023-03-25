package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Comparable<Tag> {
    private Long id;
    private String name;

    @Override
    public int compareTo(Tag tag) {
        return this.getName().compareTo(tag.getName());
    }
}
