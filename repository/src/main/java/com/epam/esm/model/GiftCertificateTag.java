package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GiftCertificateTag {
    long certificateId;
    long tagId;
}
