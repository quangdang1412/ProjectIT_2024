package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.DiscountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface IDiscountRepository extends JpaRepository<DiscountModel,String> {
    @Transactional
    @Modifying
    @Query("UPDATE DiscountModel d SET d.active = false WHERE d.endDate < ?1")
    void validDiscount(LocalDate date);
}
