package lt.ju.eshop.Category;// src/main/java/lt/ju/eshop/CategoryController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class
CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryRepo categoryRepo) {
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategoryTree() {
        List<Category> categoryTree = categoryService.getCategoryTree();
        return ResponseEntity.ok(categoryTree);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() ->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category newCategory) {
        Category savedCategory = categoryRepo.save(newCategory);
        return ResponseEntity.ok(savedCategory);
    }
}
