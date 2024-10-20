package com.webbanhang.webbanhang.Service;


import com.webbanhang.webbanhang.Model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IImageService {
    List<ImageModel> getAllImage();
    ImageModel findOneImage(String id);
    void addImage(String code);
    String upload(MultipartFile multipartFile);
}
