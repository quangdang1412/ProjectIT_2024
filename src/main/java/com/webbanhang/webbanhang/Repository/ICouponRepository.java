package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.CouponModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICouponRepository extends JpaRepository<CouponModel, String> {
}
