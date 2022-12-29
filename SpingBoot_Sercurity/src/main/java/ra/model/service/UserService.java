package ra.model.service;

import ra.model.entity.Users;

import java.util.List;

public interface UserService{
    List<Users> findAll();
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);
    Users findByUserId(int userId);


}
