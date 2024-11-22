package com.webbanhang.webbanhang.Service;


import com.webbanhang.webbanhang.Model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    List<ImageModel> getAllImage();

    ImageModel findOneImage(String id);

    void addImage(String code);

    boolean isPresent(String id);

    String upload(MultipartFile multipartFile);
}
