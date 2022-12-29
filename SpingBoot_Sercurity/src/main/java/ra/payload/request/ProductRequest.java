package ra.payload.request;

import lombok.Data;
import ra.model.entity.Catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProductRequest {
    private int productId;
    private String productName;
    private List<String> listImage = new ArrayList<>();
    private int price;
    private int quantity;
    private Date createdProduct;
    private String descriptions;
    private boolean productStatus;
    private int catalogId;

}
