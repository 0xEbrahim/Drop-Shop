package com.ibrahim.drop_shop.services.image;

import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Image;
import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.repositories.ImageRepository;
import com.ibrahim.drop_shop.services.image.DTO.ImageResponseDto;
import com.ibrahim.drop_shop.services.product.DTO.ProductResponseDto;
import com.ibrahim.drop_shop.services.product.IProductService;
import com.ibrahim.drop_shop.utils.ResponseTransformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;
    private final ResponseTransformer responseTransformer;
    @Autowired
    public ImageService(ImageRepository imageRepository, IProductService productService, ResponseTransformer responseTransformer){
        this.imageRepository = imageRepository;
        this.productService = productService;
        this.responseTransformer = responseTransformer;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found"));
    }

    @Override
    @Transactional
    public List<ImageResponseDto> saveImage(List<MultipartFile> files, Long productId) throws SQLException, IOException {
        ProductResponseDto productDto = productService.getProductById(productId);
        Product product =  responseTransformer.transformFromDto(productDto, Product.class);
        List<ImageResponseDto> savedImages = new ArrayList<>();
        for(MultipartFile file: files) {
               Image image = Image
                       .builder()
                       .fileName(file.getOriginalFilename())
                       .fileType(file.getContentType())
                       .blob(new SerialBlob(file.getBytes()))
                       .product(product)
                       .build();
               String baseDLUrl = "api/v1/images/image/download/";
               Image savedImg = imageRepository.save(image);
               savedImg.setDownloadUrl(baseDLUrl + savedImg.getId());
               savedImg = imageRepository.save(image);
                ImageResponseDto imgDto = responseTransformer.transformToDto(savedImg, ImageResponseDto.class);
                savedImages.add(imgDto);
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
    public void updateImageById(MultipartFile file, Long id) throws IOException, SQLException {
        Image image = getImageById(id);
            if( file != null) {
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setBlob(new SerialBlob(file.getBytes()));
            }
            imageRepository.save(image);
    }
}
