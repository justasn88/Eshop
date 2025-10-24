package lt.ju.eshop.Cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuantityDTO {
    @NotNull
    private Long itemId;
    @Min(0)
    private int newQuantity;
    }

