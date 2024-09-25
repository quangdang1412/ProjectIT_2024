package com.webbanhang.webbanhang.Service.Impl;

import com.webbanhang.webbanhang.DTO.request.Other.DiscountRequestDTO;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Exception.ResourceNotFoundException;
import com.webbanhang.webbanhang.Model.DiscountModel;
import com.webbanhang.webbanhang.Repository.IDiscountRepository;
import com.webbanhang.webbanhang.Service.IDiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscountServiceImpl implements IDiscountService {
    private final IDiscountRepository discountRepository;
    @Override
    public List<DiscountModel> getAllDiscount() {
        return (List<DiscountModel>) discountRepository.findAll();
    }

    @Override
    public DiscountModel findDiscountByID(String id) {
        return discountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Discount not found"));
    }

    @Override
    public String save(DiscountRequestDTO a) {
        try{
            DiscountModel discountModel = DiscountModel.builder()
                    .discountID(a.getDiscountID())
                    .percentage(a.getPercentage())
                    .startDate(Date.valueOf(a.getStartDate()))
                    .endDate(Date.valueOf(a.getEndDate()))
                    .build();
            discountRepository.save(discountModel);
            return a.getDiscountID();
        }catch (Exception e){
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".")+1,error.lastIndexOf("]"));
            log.info(e.getMessage());
            throw new CustomException(property+ " has been used");
        }
    }



}
