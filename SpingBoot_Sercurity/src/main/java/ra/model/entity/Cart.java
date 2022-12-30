package ra.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private int cartId;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;

}
