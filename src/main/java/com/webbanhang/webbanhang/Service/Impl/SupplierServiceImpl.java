package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DTO.request.Supplier.SupplierRequestDTO;
import com.webbanhang.webbanhang.Exception.DuplicateException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.SupplierModel;
import com.webbanhang.webbanhang.Repository.ISupplierRepository;
import com.webbanhang.webbanhang.Service.ISuppilerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements ISuppilerService {
    private final ISupplierRepository supplierRepository;
    @Override
    public List<SupplierModel> getAllSupplier() {
        return (List<SupplierModel>) supplierRepository.findAll();
    }

    @Override
    public SupplierModel findSupplierByID(String id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public String saveSupplier(SupplierRequestDTO a) {
        try{
            SupplierModel supplierModel = SupplierModel.builder()
                    .SupplierID(a.getSupplierID())
                    .SupplierName(a.getSupplierName())
                    .Address(a.getAddress())
                    .Email(a.getEmail())
                    .Phone(a.getPhone())
                    .build();
            supplierRepository.save(supplierModel);
            return supplierModel.getSupplierID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new DuplicateException(property+ " has been used");
        }
    }


    @Override
    public String deleteSupplier(String id) {
        try{
            SupplierModel supplierModel = findSupplierByID(id);
            supplierRepository.delete(supplierModel);
            return id;
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(error);
            throw new DuplicateException(property+ " has been used");
        }
    }
}
