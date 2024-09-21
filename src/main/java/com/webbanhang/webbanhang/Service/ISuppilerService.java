package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.DTO.request.Supplier.SupplierRequestDTO;
import com.webbanhang.webbanhang.Model.SupplierModel;

import java.util.List;

public interface ISuppilerService {
    List<SupplierModel> getAllSupplier();
    SupplierModel findSupplierByID(String id);
    String saveSupplier(SupplierRequestDTO a);

    String deleteSupplier(String id);
}
