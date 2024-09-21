package com.webbanhang.webbanhang.DAO;


import com.webbanhang.webbanhang.Model.ImageModel;

import java.util.List;

public interface IImageDAO {
    List<ImageModel> getAllImage();
    ImageModel findOneImage(String id);
    Boolean addImage(String code);
}
