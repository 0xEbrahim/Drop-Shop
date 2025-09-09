package com.ibrahim.drop_shop.controllers;

import com.ibrahim.drop_shop.models.Image;
import com.ibrahim.drop_shop.response.ApiResponse;
import com.ibrahim.drop_shop.services.image.DTO.ImageDto;
import com.ibrahim.drop_shop.services.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @Autowired
    public ImageController(IImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestBody List<MultipartFile> files,@RequestParam Long productId) {
            List<ImageDto> imagesDto = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("Success", imagesDto));
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image img = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(img.getBlob().getBytes(1,(int)img.getBlob().length()));
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(img.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+img.getFileName() +"\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) throws SQLException, IOException {

            imageService.updateImageById(file, imageId);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse("Updated Successfully", null));

    }
    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
            imageService.deleteImageById(imageId);
            return ResponseEntity
                    .ok()
                    .body(new ApiResponse("Deleted Successfully", null));
    }
}
