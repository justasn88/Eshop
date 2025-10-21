package lt.ju.eshop.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findById(long id);
    List<Category> findByParentId(Long parentId);
    List<Category> findByParentIdIsNull();

}
