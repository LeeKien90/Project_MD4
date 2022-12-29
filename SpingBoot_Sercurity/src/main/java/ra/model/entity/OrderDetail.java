package ra.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "orderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetailId")
    private int orderDetailId;
    @Column(name = "productName")
    private String productName;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "amount")
    private int amount;

    @OneToMany(mappedBy = "orderDetail")
    List<Cart> carts = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

}
