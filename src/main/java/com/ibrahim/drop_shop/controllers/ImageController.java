package com.ibrahim.drop_shop.controllers;

import com.ibrahim.drop_shop.models.Image;
import com.ibrahim.drop_shop.response.ApiResponse;
import com.ibrahim.drop_shop.services.image.DTO.ImageDto;
import com.ibrahim.drop_shop.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,@RequestParam Long productId) {
        try{
            List<ImageDto> imagesDto = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("Success", imagesDto));
        }catch(Exception e){
       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }
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
}
