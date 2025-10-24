package lt.ju.eshop.Cart;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;


@Value
@Builder
public class CartItemDTO {
    private Long itemId;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private String imageUrl;
    private BigDecimal totalPrice;


}
