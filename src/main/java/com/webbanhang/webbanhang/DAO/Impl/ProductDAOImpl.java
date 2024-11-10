package com.webbanhang.webbanhang.DAO.Impl;
import com.webbanhang.webbanhang.DAO.IProductDAO;
import com.webbanhang.webbanhang.Model.ProductModel;
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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.webbanhang.webbanhang.Util.AppConst.LIKE_FORMAT;

@Repository
public class ProductDAOImpl implements IProductDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<ProductModel> getAllProduct() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<ProductModel> query = currentSession.createQuery("from ProductModel where active = true", ProductModel.class);
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
            a.setActive(false);
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
    public Page<ProductModel> getProductForPage(Integer a, String categoryID, String brandID, String sortBy, String searchQuery) {
        Session currentSession = entityManager.unwrap(Session.class);
        StringBuilder sqlQuery = new StringBuilder("SELECT p FROM ProductModel p WHERE 1=1 and p.active=true");
        if (StringUtils.hasLength(categoryID)) {
            sqlQuery.append(" AND lower(p.category.categoryID) like lower(:categoryID)");
        }
        if (StringUtils.hasLength(brandID)) {
            sqlQuery.append(" AND lower(p.brand.brandID) like lower(:brandID)");
        }
        if (StringUtils.hasLength(searchQuery)) {
            sqlQuery.append(" AND lower(p.productName) like lower(:searchQuery)");
        }
        if (StringUtils.hasLength(sortBy)) {
            //asc|desc
            sqlQuery.append(String.format(" ORDER BY p.unitPrice %s", sortBy.toUpperCase()));
        }
        jakarta.persistence.Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        if (StringUtils.hasLength(categoryID)) {
            selectQuery.setParameter("categoryID", String.format(LIKE_FORMAT, categoryID));
        }
        if (StringUtils.hasLength(brandID)) {
            selectQuery.setParameter("brandID", String.format(LIKE_FORMAT, brandID));
        }
        if (StringUtils.hasLength(searchQuery)) {
            selectQuery.setParameter("searchQuery", String.format(LIKE_FORMAT, searchQuery));
        }
        Pageable pageable = PageRequest.of(a-1,9);
        int total = selectQuery.getResultList().size();
        selectQuery.setFirstResult((a - 1) * 9);
        selectQuery.setMaxResults(9);
        List<?> products = selectQuery.getResultList();

        Page<ProductModel> page = (Page<ProductModel>) new PageImpl<>(products,pageable,total);
        return page;
    }


}
