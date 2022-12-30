package ra.payload.response;

import lombok.Data;
import ra.model.entity.Image;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponse {
    private int cartId;
    private String productName;
    private List<Image> productImage = new ArrayList<>();
    private int quantity;
    private int price;
    private int totalPrice;

}
