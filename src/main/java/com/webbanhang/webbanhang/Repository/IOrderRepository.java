package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderModel,String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderModel o WHERE o.orderID = ?1")
    void deleteOrder(String a);

    @Transactional
    @Query("from OrderModel o where o.status = ?1")
    List<OrderModel> getOrderByStatus(String a);

}
