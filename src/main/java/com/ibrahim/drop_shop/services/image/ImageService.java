package com.ibrahim.drop_shop.services.image;

import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Image;
import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.repositories.ImageRepository;
import com.ibrahim.drop_shop.services.image.DTO.ImageDto;
import com.ibrahim.drop_shop.services.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;
    @Autowired
    public ImageService(ImageRepository imageRepository, IProductService productService){
        this.imageRepository = imageRepository;
        this.productService = productService;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found"));
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImages = new ArrayList<>();
        for(MultipartFile file: files) {
            try{
               Image image = Image
                       .builder()
                       .fileName(file.getOriginalFilename())
                       .fileType(file.getContentType())
                       .blob(new SerialBlob(file.getBytes()))
                       .product(product)
                       .build();
               String baseDLUrl = "api/v1/images/image/download/";
               image.setDownloadUrl(baseDLUrl+image.getId());
               Image savedImg = imageRepository.save(image);
               savedImg.setDownloadUrl(baseDLUrl + savedImg.getDownloadUrl());
                ImageDto imgDto = ImageDto
                        .builder()
                        .id(savedImg.getId())
                        .downloadUrl(savedImg.getDownloadUrl())
                        .fileName(savedImg.getFileName())
                        .build();
                savedImages.add(imgDto);
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImages;
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository
                .findById(id)
                .ifPresentOrElse(imageRepository::delete, () -> {throw new NotFoundException("Image not found");});
    }

    @Override
    public void updateImageById(MultipartFile file, Long id) {
        try{
            Image image = getImageById(id);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setBlob(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
