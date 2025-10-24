package lt.ju.eshop.Cart;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartMvcController {

    private final CartService cartService;
    private final CartRepo cartRepo;

    public CartMvcController(CartService cartService, CartRepo cartRepo) {
        this.cartService = cartService;
        this.cartRepo = cartRepo;
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Long clientId = cartService.getOrCreateCartId(session);

        Cart cart = cartService.getCart(clientId);

        model.addAttribute("cart", cart);

        return "cart/cart-page";

    }

    @PostMapping("/cart/update")
    public String updateItemQuantity(@RequestParam("itemId") Long itemId, @RequestParam("newQuantity") int newQuantity)
    {
        cartService.updateItemQuantity(itemId, newQuantity);
        return "redirect:/cart";
    }


    @PostMapping("/cart/remove")
    public String removeCart(@RequestParam("itemId") Long itemId)
    {
        cartService.removeItem(itemId);
        return "redirect:/cart";
    }
}
