package lt.ju.eshop.Product;

import jakarta.persistence.*;
import lt.ju.eshop.ProductImage.ProductImage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String description;
    Long categoryId;
    BigDecimal price;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ProductImage> images;

    protected Product() {}

    public Product(String name, String description, Long categoryId, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
    }
    @Override
    public String toString(){
        return String.format("Product[id=%d, name=%s, description=%s, categoryId=%d, price=%f]",
                id, name, description, categoryId, price);
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public Optional<String> getImage() {
        List<ProductImage> images = this.images;

        if (images != null || !images.isEmpty()) {
            return Optional.of(images.get(0).getPath());
        }
        return Optional.empty();
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public BigDecimal getPrice() {return price;}

}
