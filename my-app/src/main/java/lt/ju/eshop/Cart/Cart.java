package lt.ju.eshop.Cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lt.ju.eshop.Client.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
public class Cart {

    @Id
    private int id;
    private int productId;
    private int orderId;
    private int quantity;

    @MapsId
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CartItem> items;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Cart() {
    }

    public Cart(int id, int productId, int orderId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart[" +
                "id=" + id +
                ", productId=" + productId +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ']';
    }

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
