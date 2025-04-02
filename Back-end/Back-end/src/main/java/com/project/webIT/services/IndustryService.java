package com.project.webIT.services;

import com.project.webIT.models.Industry;
import com.project.webIT.repositories.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndustryService implements com.project.webIT.services.IService.IndustryService {
    private final IndustryRepository industryRepository;


    @Override
    public Industry getIndustryById(long id) {
        return null;
    }

    @Override
    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }
}
