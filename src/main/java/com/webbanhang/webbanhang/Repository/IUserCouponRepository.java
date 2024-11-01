package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.PK.UserCouponID;
import com.webbanhang.webbanhang.Model.UserCouponModel;
import com.webbanhang.webbanhang.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserCouponRepository extends JpaRepository<UserCouponModel, UserCouponID> {
    List<UserCouponModel> findByUserCoupon_UserID(String userId);
}
