package com.webbanhang.webbanhang.Repository;

import com.webbanhang.webbanhang.Model.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends JpaRepository<BrandModel, String> {
}
