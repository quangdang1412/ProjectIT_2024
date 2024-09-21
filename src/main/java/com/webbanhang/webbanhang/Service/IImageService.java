package com.webbanhang.webbanhang.Service;


import com.webbanhang.webbanhang.Model.ImageModel;

import java.util.List;

public interface IImageService {
    List<ImageModel> getAllImage();
    ImageModel findOneImage(String id);
    Boolean addImage(String code);
}
