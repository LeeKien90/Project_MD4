package ra.model.service;

import ra.model.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findAll();
    Cart save(Cart cart);
    void delete(int cartId);
    Cart findById(int cartId);
    List<Cart> findAllUserId(int userId);
}
