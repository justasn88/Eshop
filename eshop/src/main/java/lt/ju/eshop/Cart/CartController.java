package lt.ju.eshop.Cart;


import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lt.ju.eshop.Product.Product;
import lt.ju.eshop.Product.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CartController {

    private final CartService cartService;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;

    public CartController (CartService cartService, CartRepo cartRepo, CartItemRepo cartItemRepo, ProductRepo productRepo) {
        this.cartService = cartService;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCartsData(HttpSession session) {
        Long clienId = cartService.getOrCreateCartId(session);
        Cart cart = cartService.getCart(clienId);
        CartDTO cartDTO = cartService.convertToCartDTO(cart);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddToCartDTO addToCartDTO, HttpSession session) {
        Long cart_id = cartService.getOrCreateCartId(session);

        cartService.addOrUpdateCartItem(cart_id, addToCartDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateItemQuantity(@Valid @RequestBody UpdateQuantityDTO dto) {

        cartService.updateItemQuantity(dto.getItemId(), dto.getNewQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeItem(@RequestBody RemoveItemDTO dto) {
        System.out.println("Gautas itemId: " + dto.getItemId());
        if (dto.getItemId() == null) {
            return ResponseEntity.badRequest().body("itemId negali būti null.");
        }
        cartService.removeItem(dto.getItemId());
        return ResponseEntity.ok().build();
    }



}
