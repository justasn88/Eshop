package lt.ju.eshop.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveItemDTO {
    @NotNull
    private Long itemId;
}
