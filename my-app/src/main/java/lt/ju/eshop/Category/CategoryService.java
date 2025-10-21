package lt.ju.eshop.Category;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepo repository;

    public CategoryService(CategoryRepo repository) {
        this.repository = repository;
    }

    public Optional<Category> getCategoryById(Long id) {
        return repository.findById(id);
    }

    public List<Category> getCategoryTree() {
        return repository.findByParentIdIsNull();
    }
}
