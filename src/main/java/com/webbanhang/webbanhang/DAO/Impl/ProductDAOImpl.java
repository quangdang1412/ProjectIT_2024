package com.webbanhang.webbanhang.DAO.Impl;

import com.webbanhang.webbanhang.DAO.IProductDAO;
import com.webbanhang.webbanhang.Model.ImageModel;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.UserModel;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProductDAOImpl implements IProductDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<ProductModel> getAllProduct() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<ProductModel> query = currentSession.createQuery("from ProductModel where deleteProduct = 1", ProductModel.class);
        return query.getResultList();
    }

    @Override
    public ProductModel findOneProduct(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<ProductModel> query = currentSession.createQuery("from ProductModel where productID = :id", ProductModel.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    @Override
    public boolean addProduct(ProductModel a) {
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
    public boolean updateProduct(ProductModel a) {
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
    public boolean deleteProduct(ProductModel a) {
        Transaction transaction = null;
        try {
            Session currentSession = entityManager.unwrap(Session.class);
            transaction = currentSession.beginTransaction();
            a.setDeleteProduct(0);
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
    public List<ProductModel> findCategory(String id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<ProductModel> query = currentSession.createQuery("from ProductModel where category.categoryID = :categoryId", ProductModel.class);
        query.setParameter("categoryId", id);
        return query.getResultList();
    }
    @Override
    public Page<ProductModel> findCategoryForPage(String id,Integer a) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<ProductModel> query = currentSession.createQuery("from ProductModel where category.categoryID = :categoryId", ProductModel.class);
        Pageable pageable = PageRequest.of(a-1,9);
        query.setParameter("categoryId", id);
        query.setFirstResult((a - 1) * 9);
        query.setMaxResults(9);
        List<ProductModel> products = query.getResultList();
        Query<Long> countQuery = currentSession.createQuery("SELECT COUNT(p) from ProductModel p where p.category.categoryID = :categoryId", Long.class);
        countQuery.setParameter("categoryId", id);
        Long total = countQuery.getSingleResult();
        return new PageImpl<>(products,pageable,total);
    }

    @Override
    public Page<ProductModel> getProductForPage(Integer a) {
        Session currentSession = entityManager.unwrap(Session.class);
        Pageable pageable = PageRequest.of(a-1,9);
        Query<ProductModel> query = currentSession.createQuery("SELECT p FROM ProductModel p", ProductModel.class);
        query.setFirstResult((a - 1) * 9);
        query.setMaxResults(9);
        List<ProductModel> products = query.getResultList();
        Query<Long> countQuery = currentSession.createQuery("SELECT COUNT(p) FROM ProductModel p", Long.class);
        Long total = countQuery.getSingleResult();
        return new PageImpl<>(products,pageable,total);
    }

}
