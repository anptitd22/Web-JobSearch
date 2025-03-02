package com.project.webIT.services;

import com.project.webIT.dtos.companies.CompanyDTO;
import com.project.webIT.dtos.companies.CompanyImageDTO;
import com.project.webIT.exception.DataNotFoundException;
import com.project.webIT.exception.InvalidParamException;
import com.project.webIT.models.Company;
import com.project.webIT.models.CompanyImage;
import com.project.webIT.repositories.CompanyImageRepository;
import com.project.webIT.repositories.CompanyRepository;
import com.project.webIT.response.companies.CompanyResponse;
import com.project.webIT.services.IService.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyImageRepository companyImageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Company createCompany(CompanyDTO companyDTO) {
        modelMapper.typeMap(CompanyDTO.class, Company.class)
                .addMappings(mapper -> mapper.skip(Company::setId));
        Company newCompany = new Company();
        modelMapper.map(companyDTO, newCompany);
        newCompany.setActive(true);
        return companyRepository.save(newCompany);
    }

    @Override
    public Company getCompanyById(long id) throws Exception {
        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find company with id = "+id));
    }

    @Override
    public Page<CompanyResponse> getAllCompanies(PageRequest pageRequest) {
        return companyRepository.findAll(pageRequest).map(CompanyResponse::fromCompany);
    }

    @Override
    public Company updateCompany(long id, CompanyDTO companyDTO) throws Exception {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find company with id = "+id));
        if (existingCompany != null){
            modelMapper.typeMap(CompanyDTO.class, Company.class)
                    .addMappings(mapper -> mapper.skip(Company::setId));
            modelMapper.map(companyDTO, existingCompany);
            return companyRepository.save(existingCompany);
        }
        return null;
    }

    @Override
    public void deleteCompany(long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            companyRepository.deleteById(id);
        }
    }

    @Override
    public void closeCompany(long id) {
        Company existingCompany = companyRepository.findById(id).orElse(null);
        if(existingCompany != null){
            boolean active = existingCompany.isActive();
            existingCompany.setActive(!active);
            companyRepository.save(existingCompany);
        }
    }

    @Override
    public boolean existByName(String name) {
        return companyRepository.existsByName(name);
    }

    @Override
    public CompanyImage createCompanyImage(Long companyId, CompanyImageDTO companyImageDTO) throws Exception {
        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job Function with id = "
                                +companyImageDTO.getCompanyId()));
        CompanyImage newCompanyImage = CompanyImage.builder()
                .company(existingCompany)
                .imageUrl(companyImageDTO.getImageUrl())
                .build();
        //khong cho insert qua 5 anh
        int size = companyImageRepository.findByCompanyId(companyId).size();
        if(size >= CompanyImage.MAXIMUM_IMAGES_PER_COMPANY){
            throw new InvalidParamException("Number of Image must be <= " +
                    CompanyImage.MAXIMUM_IMAGES_PER_COMPANY);
        }
        return companyImageRepository.save(newCompanyImage);
    }
}
