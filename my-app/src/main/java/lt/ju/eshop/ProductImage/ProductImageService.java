package lt.ju.eshop.ProductImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    private final ProductImageRepo repository;

    @Autowired
    public ProductImageService(ProductImageRepo repository) {
        this.repository = repository;
    }

    public List<ProductImage> getProductImages() {
        return repository.findAll();
    }

    public Optional<ProductImage> getProductImageById(Long id) {
        return repository.findById(id);
    }
}
