package lt.ju.eshop.Client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ClientCheckoutDTO {

    @NotBlank(message = "El. paštas yra privalomas")
    @Email(message = "Netinkamas formatas")
    private String email;

    @NotBlank(message = "Vardas yra privalomas")
    private String name;

    @NotBlank(message = "Pavardė yra privaloma")
    private String lastName;

    @NotBlank(message = "Adresas yra privalomas")
    private String address;

    @NotBlank(message = "Telefono numeris yra privalomas")
    private String phone;


}
