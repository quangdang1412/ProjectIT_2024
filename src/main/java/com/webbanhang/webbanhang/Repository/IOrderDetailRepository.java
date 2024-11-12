package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.OrderDetailModel;
import com.webbanhang.webbanhang.Model.PK.OrderDetailID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetailModel, OrderDetailID> {

    @Query(value = "SELECT od.productOrder.productID, SUM(od.quantity) FROM OrderDetailModel od GROUP BY od.productOrder.productID ORDER BY SUM(od.quantity) DESC")
    List<Object[]> getBestSellingProducts();
}
