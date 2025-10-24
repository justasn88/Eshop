package lt.ju.eshop.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartClientIdAndProductId(Long cartClientId, Long productId);
}
