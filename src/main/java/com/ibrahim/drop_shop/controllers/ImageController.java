package com.ibrahim.drop_shop.controllers;

import com.ibrahim.drop_shop.models.Image;
import com.ibrahim.drop_shop.services.image.DTO.ImageResponseDto;
import com.ibrahim.drop_shop.utils.ApiResponse;
import com.ibrahim.drop_shop.services.image.IImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse> saveImages(@RequestBody List<MultipartFile> files,@RequestParam Long productId) throws SQLException, IOException {
            List<ImageResponseDto> imagesDto = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("Success", imagesDto));
    }

    @GetMapping("/image/download/{imageId}")
    @Transactional
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
            return ApiResponse.sendResponse("Updated Successfully",HttpStatus.OK, null);

    }
    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
            imageService.deleteImageById(imageId);
            return ApiResponse.sendResponse("Deleted Successfully", HttpStatus.OK,null);
    }
}
