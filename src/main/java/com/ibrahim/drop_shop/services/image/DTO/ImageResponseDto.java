package com.ibrahim.drop_shop.services.image.DTO;


import lombok.Data;

@Data
public class ImageResponseDto {
    private Long id;
    private String fileName;
    private String downloadUrl;
    private String fileType;
}
