package ra.payload.response;

import lombok.Data;

@Data
public class CartResponse {
    private int cartId;
    private String productName;
    private int quantity;
    private int price;
    private int totalPrice;
}
