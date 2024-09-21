package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.OrderModel;
import com.webbanhang.webbanhang.Model.UserModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IOrderRepository extends CrudRepository<OrderModel,String>,JpaSpecificationExecutor<OrderModel> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderModel o WHERE o.OrderID = ?1")
    void deleteOrder(String a);

    @Transactional
    @Query("from OrderModel o where o.Status = ?1")
    List<OrderModel> getOrderByStatus(String a);

}
