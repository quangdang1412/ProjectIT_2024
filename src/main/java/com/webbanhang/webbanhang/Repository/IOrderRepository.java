package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderModel, String> {

    @Modifying
    @Transactional
    @Query("UPDATE OrderModel o SET o.status='Đã hủy đơn' WHERE o.orderID = ?1")
    void deleteOrder(String a);

    @Transactional
    @Query("from OrderModel o where o.status = ?1")
    List<OrderModel> getOrderByStatus(String a);

    @Transactional
    @Query("from OrderModel o where o.orderDate >= ?1 and o.orderDate <= ?2 and o.status = 'Hoàn thành' order by o.orderDate ")
    List<OrderModel> revenue(LocalDate startDate, LocalDate endDate);

    @Transactional
    @Query("SELECT p.productName, SUM(od.quantity) as Q FROM OrderDetailModel od JOIN od.productOrder p JOIN od.order o WHERE o.orderDate BETWEEN ?1 AND ?2 AND o.status = 'Hoàn thành' GROUP BY p.productName ORDER BY Q DESC")
    List<Object[]> topSeller(LocalDate startDate, LocalDate endDate);

    @Transactional
    @Query("SELECT count(od.orderID) FROM OrderModel od WHERE od.status = 'Chờ xác nhận' or od.status = 'Đã xác nhận'")
    Integer countOrderNeedToProcess();

    @Transactional
    @Query("SELECT sum(od.totalPrice + od.transportFee) FROM OrderModel od WHERE od.status = 'Hoàn thành'")
    Double revenueTotal();

    @Transactional
    @Query("SELECT sum(od.totalPrice + od.transportFee) from OrderModel od where od.orderDate >= ?1 and od.orderDate <= ?2 and od.status = 'Hoàn thành'")
    Double revenuePerMonth(LocalDate startDate, LocalDate endDate);

    @Transactional
    @Query("SELECT SUM(od.quantity * (p.unitPrice-p.unitCost)) as Q FROM OrderDetailModel od JOIN od.productOrder p JOIN od.order o WHERE  o.status = 'Hoàn thành'")
    Double profitTotal();
}
