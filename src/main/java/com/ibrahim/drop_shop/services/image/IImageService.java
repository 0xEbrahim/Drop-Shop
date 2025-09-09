package com.ibrahim.drop_shop.services.image;

import com.ibrahim.drop_shop.models.Image;
import com.ibrahim.drop_shop.services.image.DTO.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    void deleteImageById(Long id);
    void updateImageById(MultipartFile file, Long id) throws IOException, SQLException;

}
