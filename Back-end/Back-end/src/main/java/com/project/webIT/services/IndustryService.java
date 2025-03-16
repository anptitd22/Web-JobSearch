package com.project.webIT.services;

import com.project.webIT.models.Industry;
import com.project.webIT.repositories.IndustryRepository;
import com.project.webIT.services.IService.IIndustryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndustryService implements IIndustryService {
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
