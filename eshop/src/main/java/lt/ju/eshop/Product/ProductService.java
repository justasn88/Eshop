package lt.ju.eshop.Product;


import lt.ju.eshop.Category.Category;
import lt.ju.eshop.Category.CategoryRepo;
import lt.ju.eshop.ProductImage.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    @Autowired
    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        List<Long> allCategoryIds = getAllCategoryIds(categoryId);
        return productRepo.findByCategoryIdIn(allCategoryIds);
    }
    private List<Long> getAllCategoryIds(Long parentId) {
        List<Long> ids = new ArrayList<>();
        ids.add(parentId);

        List<Category> children = categoryRepo.findByParentId(parentId);
        for (Category child : children) {
            ids.addAll(getAllCategoryIds(child.getId()));
        }
        return ids;
    }

}
