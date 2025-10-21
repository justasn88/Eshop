package lt.ju.eshop.Category;


import java.util.ArrayList;


public class CategoryDTO {

    private Long id;
    private String name;
    private ArrayList<CategoryDTO> children = new ArrayList<>();

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<CategoryDTO> getChildren() {
        return children;
    }

    public void addChild(CategoryDTO child) {
        this.children.add(child);
    }
}
