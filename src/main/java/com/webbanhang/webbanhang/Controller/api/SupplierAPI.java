package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.request.Supplier.SupplierRequestDTO;
import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.DTO.response.ResponseError;
import com.webbanhang.webbanhang.Exception.DuplicateException;
import com.webbanhang.webbanhang.Service.ISuppilerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
@Validated
@Slf4j
@RequiredArgsConstructor
public class SupplierAPI {
    private final ISuppilerService supplierService;
    @PostMapping("/add")
    public ResponseData<String> addUser(@Valid @RequestBody SupplierRequestDTO supplierDTO)
    {
        try{
            log.info("Request add supplier: {}",supplierDTO.getSupplierName());
            String userId = supplierService.saveSupplier(supplierDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof DuplicateException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Save failed");
        }
    }
    @PutMapping("/update")
    public ResponseData<String>  updateUser(@Valid @RequestBody SupplierRequestDTO supplierDTO)
    {
        try{
            log.info("Request update user: {}",supplierDTO.getSupplierName());
            String userId = supplierService.saveSupplier(supplierDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof DuplicateException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update failed");
        }
    }
    @DeleteMapping("/delete/{supplierID}")
    public ResponseData<String>  deleteUser(@PathVariable("supplierID")  String id)
    {
        try{
            String supplierId = supplierService.deleteSupplier(id);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",supplierId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete failed");
        }
    }
}
