package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.SupplierModel;
import com.webbanhang.webbanhang.Model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierRepository extends CrudRepository<SupplierModel,String> {
}
