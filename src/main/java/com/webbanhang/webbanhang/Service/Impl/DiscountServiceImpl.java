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
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscountServiceImpl implements IDiscountService {
    private final IDiscountRepository discountRepository;

    @Override
    public List<DiscountModel> getAllDiscount() {
        return discountRepository.findAll();
    }

    @Override
    public List<DiscountModel> getAllDiscountActive() {
        LocalDate date = LocalDate.now();
        discountRepository.validDiscount(date);
        return discountRepository.findAll().stream().filter(DiscountModel::isActive).toList();
    }

    @Override
    public DiscountModel findDiscountByID(String id) {
        return discountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
    }

    @Override
    public String save(DiscountRequestDTO a) {
        try {
            DiscountModel discountModel = DiscountModel.builder()
                    .discountID(a.getDiscountID())
                    .percentage(a.getPercentage())
                    .startDate(Date.valueOf(a.getStartDate()))
                    .endDate(Date.valueOf(a.getEndDate()))
                    .active(true)
                    .build();
            discountRepository.save(discountModel);
            return a.getDiscountID();
        } catch (Exception e) {
            String error = e.getMessage();
            String property = error.substring(error.lastIndexOf(".") + 1, error.lastIndexOf("]"));
            log.info(e.getMessage());
            throw new CustomException(property + " has been used");
        }
    }


}
