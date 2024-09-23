package com.webbanhang.webbanhang.DAO.Impl;

import com.webbanhang.webbanhang.DAO.IImageDAO;
import com.webbanhang.webbanhang.Model.ImageModel;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
@Repository
public class ImageDAOImpl implements IImageDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<ImageModel> getAllImage() {
        Session currentSession=entityManager.unwrap(Session.class);
        Query<ImageModel> query=currentSession.createQuery("from ImageModel ", ImageModel.class);
        return query.getResultList();
    }

    @Override
    public ImageModel findOneImage(String id) {
        Session currentSession=entityManager.unwrap(Session.class);
        Query<ImageModel> query=currentSession.createQuery("from ImageModel where imageCode= :id", ImageModel.class);
        query.setParameter("id",id);
        return query.uniqueResult();
    }

    @Override
    public Boolean addImage(String code) {
        try {
            Session currentSession = entityManager.unwrap(Session.class);
            ImageModel imageModel = new ImageModel();
            imageModel.setImageCode(code);
            currentSession.save(imageModel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
