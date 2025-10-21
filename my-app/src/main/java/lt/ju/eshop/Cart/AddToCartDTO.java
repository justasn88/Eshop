package lt.ju.eshop.Cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class AddToCartDTO {

    @NotNull(message = "Prekes id yra privalomas")
    private Long productId;

    @NotNull(message = "Kiekis yra privalomas")
    @Min(value = 1, message = "Kiekis turi bent 1")
    private Integer quantity;

}
