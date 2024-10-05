package com.webbanhang.webbanhang.Repository;

import com.google.cloud.storage.Option;
import com.webbanhang.webbanhang.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<UserModel,String> {
    @Query(value = "select u from UserModel u where u.email = ?1")
    UserModel getUserByEmail(String email);

    @Query(value = "from UserModel u where u.email = ?1")
    UserModel getUserByPhone(String phone);

    @Query(value = "from UserModel u where u.role.type = ?1")
    List<UserModel> getAllUserByType(Integer id);

    UserModel findByUserID(String userId);

    UserModel findByEmail(String email);
}
