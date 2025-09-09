package com.ibrahim.drop_shop.services.image;

import com.ibrahim.drop_shop.models.Image;
import com.ibrahim.drop_shop.services.image.DTO.ImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    List<ImageResponseDto> saveImage(List<MultipartFile> file, Long productId) throws SQLException, IOException;
    void deleteImageById(Long id);
    void updateImageById(MultipartFile file, Long id) throws IOException, SQLException;

}
