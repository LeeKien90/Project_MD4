package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Users")
@Data //@Data bao gồm cả getter và setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id tự tăng
    @Column(name = "UserId")
    private int userId;
    @Column(name = "UserName", unique = true,nullable = false)
    private String userName;
    @Column(name = "Password",nullable = false)
    private String password;
    @Column(name = "Created")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
    @Column(name = "Email",nullable = false,unique = true)
    private String email;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "UserStatus")
    private boolean userStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role",joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Roles> listRoles = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;
}
