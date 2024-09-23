package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.UserModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends CrudRepository<UserModel,String>, JpaSpecificationExecutor<UserModel> {
    @Query(value = "select u from UserModel u where u.Email = ?1")
    UserModel getUserByEmail(String email);

    @Query(value = "from UserModel u where u.Email = ?1")
    UserModel getUserByPhone(String phone);

    @Query(value = "from UserModel u where u.Role.Type = ?1")
    List<UserModel> getAllUserByType(Integer id);
}
