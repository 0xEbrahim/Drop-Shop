package com.ibrahim.drop_shop.services.image.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {

    private Long id;

    private String fileName;

    private String downloadUrl;
}
