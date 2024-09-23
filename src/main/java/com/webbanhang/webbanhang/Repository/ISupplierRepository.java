package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierRepository extends JpaRepository<SupplierModel,String> {
}
