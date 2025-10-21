package lt.ju.eshop.Client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lt.ju.eshop.Cart.Cart;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Client
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String address;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;


    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    public Client () {}

    public Client(Long id, String address, String email, String lastName, String name) {
        this.id = id;
        this.address = address;
        this.email = email;
        this.lastName = lastName;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", cart=" + cart +
                ']';
    }
}
