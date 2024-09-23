package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleModel,Integer> {
}
