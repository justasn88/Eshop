package lt.ju.eshop.Category;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "parent_category_id")
    private Long parentId;

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Category> children;

    protected Category() {}

    public Category(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return String.format("Category[id=%d, name=%s, parentId=%d]",
                id, name, parentId);
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public List<Category> getChildren() {
        return children;
    }
}
