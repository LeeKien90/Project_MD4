package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
