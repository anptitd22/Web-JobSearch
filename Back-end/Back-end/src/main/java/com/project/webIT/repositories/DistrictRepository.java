package com.project.webIT.repositories;

import com.project.webIT.models.District;
import com.project.webIT.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByProvinceId(Long provinceId);
}
