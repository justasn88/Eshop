package lt.ju.eshop.ProductImage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productImage")
public class ProductImageController {
    private final ProductImageService product_imageService;
    private final ProductImageRepo product_imageRepo;

    @Autowired
    public ProductImageController(ProductImageService product_imageService, ProductImageRepo product_imageRepo) {
        this.product_imageService = product_imageService;
        this.product_imageRepo = product_imageRepo;
    }

    @GetMapping
    public List<ProductImage> getProductImages() {
        return product_imageService.getProductImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImage> getProductImageById(@PathVariable Long id) {
        Optional<ProductImage> product_image = product_imageService.getProductImageById(id);
        return product_image.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
