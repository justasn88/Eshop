package lt.ju.eshop.Cart;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lt.ju.eshop.Category.CategoryService;
import lt.ju.eshop.Client.Client;
import lt.ju.eshop.Client.ClientRepo;
import lt.ju.eshop.Product.Product;
import lt.ju.eshop.Product.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CartService {
    private final CartRepo cartRepository;
    private final ClientRepo clientRepository;
    private final ProductRepo productRepository;
    private final CartItemRepo cartItemRepository;


    public CartService(CartRepo cartRepository, ClientRepo clientRepository, ProductRepo productRepo, CartItemRepo cartItemRepo) {
        this.cartRepository = cartRepository;
        this.clientRepository = clientRepository;
        productRepository = productRepo;
        cartItemRepository = cartItemRepo;
    }

    @Transactional
    public Long getOrCreateCartId(HttpSession session) {
        Long clientId = (Long) session.getAttribute("anonClientId");
        if (clientId == null) {
            Client anonClient = new Client();
            anonClient.setName("Anon");
            anonClient.setLastName("Guest");
            anonClient.setEmail("anonGuest" + System.currentTimeMillis() + "@eshop.lt");
            anonClient.setAddress("Nenurodyta");
            anonClient = clientRepository.save(anonClient);

            Cart newCart = new Cart();
            newCart.setClient(anonClient);
            cartRepository.save(newCart);

            clientId = anonClient.getId();
            session.setAttribute("anonClientId", clientId);
        }
        return clientId;
    }

    public void addOrUpdateCartItem(Long clientId, AddToCartDTO addToCart) {

        Cart cart = cartRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Krepselis" + clientId + "nerastas"));
        Product product = productRepository.findById(addToCart.getProductId()).orElseThrow(() -> new IllegalArgumentException("Preke su" + addToCart.getProductId() + "neegzistuoja"));

        Optional<CartItem> existingItem = cartItemRepository.findByCartClientIdAndProductId(clientId, addToCart.getProductId());

        CartItem item;

        if (existingItem.isPresent()) {
            item = existingItem.get();
            item.setQuantity(item.getQuantity() + addToCart.getQuantity());
        } else {
            item = new CartItem();
            item.setQuantity(addToCart.getQuantity());
            item.setProduct(product);
            item.setCart(cart);

            item.setPrice(product.getPrice());
        }
        if (item.getQuantity() <= 0) {
            cartItemRepository.delete(item);
        } else {
            cartItemRepository.save(item);
        }

        BigDecimal currentPrice = product.getPrice();

        if (currentPrice == null) {
            throw new IllegalStateException("Negalima pridėti prekės ID #" + addToCart.getProductId() + ". Trūksta galiojančios kainos (NULL).");
        }

        if (existingItem.isPresent()) {

            existingItem.get().setPriceAtPurchase(currentPrice);
            cartItemRepository.save(existingItem.get());
        } else {
            CartItem newItem = new CartItem();
            newItem.setPriceAtPurchase(currentPrice);

            updateCartTotal(cart);
        }
    }

    public void updateCartTotal(Cart cart) {
        BigDecimal newTotal = BigDecimal.ZERO;
        if(cart.getItems() != null) {
            newTotal = cart.getItems().stream().map(item -> item.getPrice().
                    multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        cart.setTotalPrice(newTotal);
        cartRepository.save(cart);
    }

    public Cart getCart(Long clientId) {
        return cartRepository.findById(clientId).orElseThrow (() ->new RuntimeException("Krepselis nerastas"));
    }

    @Transactional
    public void updateItemQuantity(Long itemId, int newQuantity) {
        CartItem item = cartItemRepository.findById(itemId).orElseThrow (() ->new RuntimeException("Krepselis nerastas"));

        Cart cart = item.getCart();
        if(newQuantity <= 0) {
            cartItemRepository.delete(item);
        }
        else {
            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        }
        updateCartTotal(cart);
    }

    @Transactional
    public void removeItem(Long itemId) {
        CartItem itemToDelete = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Krepšelio produktas nerastas."));
        Cart cart = itemToDelete.getCart();
        boolean removed = cart.getItems().remove(itemToDelete);
        if (!removed) {
            System.err.println("ĮSPĖJIMAS: CartItem nebuvo rastas Cart sąraše!");
        }
        cartRepository.save(cart);
        if (cartItemRepository.existsById(itemToDelete.getId())) {
            cartItemRepository.delete(itemToDelete);
        }
        updateCartTotal(cart);
    }

    public CartDTO convertToCartDTO(Cart cart) {
        List<CartItemDTO> itemDTOs = cart.getItems().stream().map(item -> CartItemDTO.builder().
                itemId(item.getId()).
                productId(item.getProduct().getId()).
                productName(item.getProduct().getName()).
                quantity(item.getQuantity()).
                price(item.getPrice()).
                imageUrl(item.getProduct().getImage().orElse("/images/placeholder.png")).
                totalPrice(item.getPrice().multiply(new BigDecimal(item.getQuantity()))).
                build()).collect(Collectors.toList());

        return CartDTO.builder().
                clientId(cart.getId()).
                totalPrice(cart.getTotalPrice()).
                totalQuantity(itemDTOs.stream().mapToInt(CartItemDTO::getQuantity).sum()).
                cartItems(itemDTOs).build();

    }

}
