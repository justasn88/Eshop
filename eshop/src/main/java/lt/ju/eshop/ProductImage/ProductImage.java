package lt.ju.eshop.ProductImage;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lt.ju.eshop.Product.Product;

@Entity
public class ProductImage {
    @Id
    private Long id;
    private String path;;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    protected ProductImage() {};

    public ProductImage(Long id, String path, Long productId) {
        this.id = id;
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("Product_image(id = %d, path = %s, productId = %d", id, path, product.getId());
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Product getProduct() {
        return product;
    }
}
