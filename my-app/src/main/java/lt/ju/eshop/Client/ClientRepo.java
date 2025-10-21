package lt.ju.eshop.Client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Integer> {
    public Client findByEmail(String email);
}
