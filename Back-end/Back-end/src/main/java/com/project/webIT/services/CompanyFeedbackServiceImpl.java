package com.project.webIT.services;

import com.project.webIT.dtos.request.CompanyFeedbackDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.Company;
import com.project.webIT.models.CompanyFeedback;
import com.project.webIT.repositories.AppliedJobRepository;
import com.project.webIT.repositories.CompanyRepository;
import com.project.webIT.repositories.CompanyFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyFeedbackServiceImpl implements com.project.webIT.services.IService.CompanyFeedbackService {
    private final CompanyFeedbackRepository companyFeedbackRepository;
    private final CompanyRepository companyRepository;
    private final AppliedJobRepository appliedJobRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompanyFeedback createCompanyFeedback(CompanyFeedbackDTO companyFeedbackDTO) throws Exception {
        Company existingCompany = companyRepository.findById(companyFeedbackDTO.getCompanyId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find company with id = "+companyFeedbackDTO.getCompanyId()));
        AppliedJob existingAppliedJob = appliedJobRepository.findById(companyFeedbackDTO.getAppliedJobId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find applied job with id = "+companyFeedbackDTO.getAppliedJobId()));
        modelMapper.typeMap(CompanyFeedbackDTO.class, CompanyFeedback.class)
                .addMappings(mapper -> mapper.skip(CompanyFeedback::setId));
        CompanyFeedback companyFeedback = new CompanyFeedback();
        modelMapper.map(companyFeedbackDTO, companyFeedback);
        companyFeedback.setCreatedAt(LocalDateTime.now());
        companyFeedback.setCompany(existingCompany);
        companyFeedback.setAppliedJob(existingAppliedJob);
        existingAppliedJob.setFeedBackDate(LocalDateTime.now());
        if (!companyFeedback.getStatus().equalsIgnoreCase(CompanyFeedback.PASS)
        && !companyFeedback.getStatus().equalsIgnoreCase(CompanyFeedback.FAIL)){
            throw new InvalidParamException("Only Pass or Fail");
        }
        appliedJobRepository.save(existingAppliedJob);
        return companyFeedbackRepository.save(companyFeedback);
    }

    @Override
    public CompanyFeedback getCompanyFeedback(Long id) throws Exception {
        return companyFeedbackRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find company response with id = "+id));
    }

    @Override
    public List<CompanyFeedback> findByCompanyId(Long companyId) {
        return companyFeedbackRepository.findByCompanyId(companyId);
    }

    @Override
    public void deleteCompanyFeedback(Long id) {
        Optional<CompanyFeedback> companyFeedbackOptional = companyFeedbackRepository.findById(id);
        if(companyFeedbackOptional.isPresent()){
            companyFeedbackRepository.deleteById(id);
        }
    }
}
