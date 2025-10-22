package lt.ju.eshop.Cart;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lt.ju.eshop.Product.Product;
import lt.ju.eshop.Product.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

    public CartController (CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddToCartDTO addToCartDTO, HttpSession session) {
        Long cart_id = cartService.getOrCreateCartId(session);

        cartService.addOrUpdateCartItem(cart_id, addToCartDTO);
        return ResponseEntity.ok().build();
    }
}
