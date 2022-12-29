package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;
    @Column(name = "productName",unique = true)
    private String productName;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "createdProduct")
    private Date createdProduct;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "productStatus")
    private boolean productStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalogId")
    private Catalog catalog;
    @OneToMany(mappedBy = "product")
    List<Image> listImage = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "orderMany",joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "orderDetailId"))
    private Set<OrderDetail> listOrderDetail = new HashSet<>();
}
