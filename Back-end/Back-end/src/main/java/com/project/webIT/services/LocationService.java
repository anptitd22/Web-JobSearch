package com.project.webIT.services;

import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.models.District;
import com.project.webIT.models.Province;
import com.project.webIT.repositories.DistrictRepository;
import com.project.webIT.repositories.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    public List<Province> getProvinceList() {
        return provinceRepository.findAll();
    }

    public Province getProvinceById(Long id) throws Exception{
        return provinceRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("not found province"));
    }
}
