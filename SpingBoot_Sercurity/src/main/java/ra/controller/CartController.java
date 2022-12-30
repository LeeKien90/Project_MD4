package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Cart;
import ra.model.repository.CatalogRepository;
import ra.model.service.CartService;
import ra.model.service.ProductService;
import ra.model.service.UserService;
import ra.payload.request.CartRequest;
import ra.payload.response.CartResponse;
import ra.sercurity.CustomUserDetail;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private CatalogRepository catalogRepository;

    @GetMapping
    public ResponseEntity<?> getAllCart() {
        CustomUserDetail customUserDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Cart> listCart = cartService.findAllUserId(customUserDetails.getUserId());
        List<CartResponse> listCartRespose = new ArrayList<>();
        for (Cart cart : listCart) {
            CartResponse cartRespose = new CartResponse();
            cartRespose.setCartId(cart.getCartId());
            cartRespose.setQuantity(cart.getQuantity());
            cartRespose.setPrice(cart.getProduct().getPrice());
            cartRespose.setTotalPrice(cart.getPrice());
            cartRespose.setProductName(cart.getProduct().getProductName());
            cartRespose.setProductImage(cart.getProduct().getListImage());
            listCartRespose.add(cartRespose);
        }
        return ResponseEntity.ok(listCartRespose);
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {

        CustomUserDetail customUserDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean check =true;
        List<Cart> listCart = cartService.findAllUserId(customUserDetails.getUserId());
        Cart cart = new Cart();
        boolean checkexit = false;
        try {
            for (Cart cart1 :listCart) {
                if (cart1.getProduct().getProductId()==cartRequest.getProductId()){
                    cart = cart1;
                    checkexit = true;
                    break;
                }
            }
            if (checkexit){
                cart.setQuantity(cart.getQuantity()+cartRequest.getQuantity());
                cart.setPrice(cart.getProduct().getPrice()*cart.getQuantity());
                cart = cartService.save(cart);

            }else {
                cart.setQuantity(cartRequest.getQuantity());
                cart.setProduct(productService.findById(cartRequest.getProductId()));
                cart.setPrice(cart.getProduct().getPrice()*cart.getQuantity());
                cart.setUsers(userService.findByUserId(customUserDetails.getUserId()));
                cart = cartService.save(cart);
            }
        }catch (Exception e){
            check = false;
            e.printStackTrace();
        }
        if (check){

            return ResponseEntity.ok("Successfully");
        }else {

            return ResponseEntity.ok("Error");
        }
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@RequestParam("quantity") int quantity, @PathVariable("cartId") int cartId) {
        try {
            Cart cart = cartService.findById(cartId);
            if (quantity>0) {
                cart.setQuantity(quantity);
                cart.setPrice(cart.getProduct().getPrice()*cart.getQuantity());
                cartService.save(cart);
            }else {
                cartService.delete(cartId);
            }
            return ResponseEntity.ok("Successfully");
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.ok("Error");
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable("cartId") int cartId){
        cartService.delete(cartId);
        return ResponseEntity.ok("Complete");
    }
}
