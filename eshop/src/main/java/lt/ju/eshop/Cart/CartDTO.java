package lt.ju.eshop.Cart;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class CartDTO {
    private Long clientId;
    private BigDecimal totalPrice;
    private int totalQuantity;
    private List<CartItemDTO> cartItems;

}
