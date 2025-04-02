package com.project.webIT.repositories;

import com.project.webIT.models.JobViewHistory;
import com.project.webIT.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
}
