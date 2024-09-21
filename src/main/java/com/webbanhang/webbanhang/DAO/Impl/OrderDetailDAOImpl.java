package com.webbanhang.webbanhang.DAO.Impl;

import com.webbanhang.webbanhang.DAO.IOrderDetailDAO;
import com.webbanhang.webbanhang.Model.OrderDetailModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

@Repository
public class OrderDetailDAOImpl implements IOrderDetailDAO {
   

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<OrderDetailModel> getAllOrderDetail() {
        Session currentSession=entityManager.unwrap(Session.class);
        Query<OrderDetailModel> query=currentSession.createQuery("from OrderDetailModel ", OrderDetailModel.class);
        return query.getResultList();
    }
    @Override
    public List<Object[]> getBestSellingProducts() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Object[]> query = currentSession.createQuery(
                "SELECT od.ProductOrder.id, SUM(od.Quantity) " +
                        "FROM OrderDetailModel od " +
                        "GROUP BY od.ProductOrder.id " +
                        "ORDER BY SUM(od.Quantity) DESC", Object[].class);
        return query.getResultList();
    }
    
}
