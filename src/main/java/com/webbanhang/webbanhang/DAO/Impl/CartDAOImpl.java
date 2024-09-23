package com.webbanhang.webbanhang.DAO.Impl;

import com.webbanhang.webbanhang.DAO.ICartDAO;
import com.webbanhang.webbanhang.Model.CartModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CartDAOImpl implements ICartDAO {
    

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<CartModel> getAllCart() {
        Session currentSession=entityManager.unwrap(Session.class);
        Query<CartModel> query=currentSession.createQuery("from CartModel ", CartModel.class);
        return query.getResultList();
    }

    @Override
    public List<ProductModel> getProductInCart(String id) {
        Session currentSession=entityManager.unwrap(Session.class);
        Query<ProductModel> query=currentSession.createQuery("select c.productCart from CartModel c where c.userCart.userID = :id ", ProductModel.class);
        query.setParameter("id",id);
        return query.getResultList();
    }

    @Override
    public CartModel findCart(String Uid, String Pid) {
        Session currentSession=entityManager.unwrap(Session.class);
        Query<CartModel> query=currentSession.createQuery("from CartModel c where c.userCart.userID = :Uid and c.productCart.productID = :Pid", CartModel.class);
        query.setParameter("Uid",Uid);
        query.setParameter("Pid",Pid);
        return query.uniqueResult();
    }

    @Override
    public boolean addCart(CartModel a) {
        Transaction transaction = null;
        try {
            Session currentSession = entityManager.unwrap(Session.class);
            transaction = currentSession.beginTransaction();
            currentSession.save(a);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCart(CartModel a) {
        Transaction transaction = null;
        try {
            Session currentSession = entityManager.unwrap(Session.class);
            transaction = currentSession.beginTransaction();
            currentSession.update(a);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCart(CartModel a) {
        Transaction transaction = null;
        try {
            Session currentSession = entityManager.unwrap(Session.class);
            transaction = currentSession.beginTransaction();
            MutationQuery query=currentSession.createMutationQuery("delete from CartModel c where c.userCart.userID = :Uid and c.productCart.productID = :Pid");
            query.setParameter("Uid",a.getUserCart().getUserID());
            query.setParameter("Pid",a.getProductCart().getProductID());
            int s =query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }   
    @Override
    public CartModel findCartItemByUserAndProduct(String userId, ProductModel product) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<CartModel> query = currentSession.createQuery(
            "FROM CartModel c WHERE c.userCart.userID = :userId AND c.productCart = :product",
            CartModel.class
        );
        query.setParameter("userId", userId);
        query.setParameter("product", product);
        return query.uniqueResult();
    }
    
}
