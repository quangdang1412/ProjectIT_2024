package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DAO.IImageDAO;
import com.webbanhang.webbanhang.Model.ImageModel;
import com.webbanhang.webbanhang.Service.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService {

    private final IImageDAO imageDAO;
    @Override
    public List<ImageModel> getAllImage() {
        return null;
    }

    @Override
    public ImageModel findOneImage(String id) {
        return imageDAO.findOneImage(id);
    }

    @Override
    public Boolean addImage(String code) {
        return imageDAO.addImage(code);
    }
}
