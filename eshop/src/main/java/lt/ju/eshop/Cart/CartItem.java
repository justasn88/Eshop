package lt.ju.eshop.Cart;

import lt.ju.eshop.Product.Product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

@Entity
@Table(name ="cart_item", schema = "public")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_client_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    @Column(name = "price_at_purchase", nullable = false)
    private BigDecimal price;


    public void setPriceAtPurchase(BigDecimal currentPrice) {
        if (currentPrice == null) {
            this.price = BigDecimal.ZERO;
        } else {
            this.price = currentPrice;
        }
    }
}
