package ra.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalogId")
    private int catalogId;
    @Column(name = "catalogName", unique = true)
    private String catalogName;
    @Column(name = "catalogStatus")
    private boolean catalogStatus;

}
