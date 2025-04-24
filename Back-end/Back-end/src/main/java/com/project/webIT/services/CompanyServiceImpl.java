package com.project.webIT.services;

import com.project.webIT.constant.JobStatus;
import com.project.webIT.dtos.response.JobResponse;
import com.project.webIT.helper.JwtTokenHelper;
import com.project.webIT.dtos.request.CompanyDTO;
import com.project.webIT.dtos.request.CompanyImageDTO;
import com.project.webIT.dtos.request.CompanyLoginDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.models.Company;
import com.project.webIT.models.CompanyImage;
import com.project.webIT.models.Job;
import com.project.webIT.repositories.CompanyDashBoardRepository;
import com.project.webIT.repositories.CompanyImageRepository;
import com.project.webIT.repositories.CompanyRepository;
import com.project.webIT.repositories.JobRepository;
import com.project.webIT.dtos.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements com.project.webIT.services.IService.CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyDashBoardRepository companyDashBoardRepository;
    private final CompanyImageRepository companyImageRepository;
    private final JobRepository jobRepository;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtTokenHelper jwtTokenHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String loginCompany(CompanyLoginDTO companyLoginDTO) throws Exception {
        if (companyLoginDTO.getRoleID() != 2) {
            throw new Exception("Bạn cần đăng nhập bằng tài khoản công ty");
        }
        // Dùng AuthenticationManager để uỷ quyền cho CompanyAuthenticationProvider xử lý xác thực
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        companyLoginDTO.getAccount(),
                        companyLoginDTO.getPassword()
                )
        );
        // Lấy thông tin company sau khi xác thực thành công
        Company company = (Company) authentication.getPrincipal();
        // Tạo và trả về token
        return jwtTokenHelper.generateTokenFromCompany(company);
    }

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
    public Page<JobResponse> getJobs(Long companyId, String keyword, Long jobFunctionId, PageRequest pageRequest, JobStatus jobStatus){
        var jobPage = jobRepository.searchJobsFromCompany(companyId, keyword, jobFunctionId, pageRequest, jobStatus);
        return jobPage.map(JobResponse::fromJob);
    }

    @Override
    public Page<CompanyResponse> getAllCompanies(String keyword, Long industryId, PageRequest pageRequest) {
        Page<Company> companyPage = companyRepository.searchCompanies(keyword, industryId, pageRequest);
        return companyPage.map(CompanyResponse::fromCompany);
    }

    @Override
    public String getPublicId(Long companyId) throws Exception {
        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new DataNotFoundException("company not found"));
        return existingCompany.getPublicIdImages();
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
    public Company getCompanyDetail(String token) throws Exception {
        if(jwtTokenHelper.isTokenExpired(token)){
            throw new Exception("ForgotToken is expired");
        }
        String account = jwtTokenHelper.extractSubject(token);
        Optional<Company> company = companyRepository.findByAccount(account);
        if (company.isEmpty()) {
            throw new DataNotFoundException("Company not found");
        }
        return company.get();
    }

    @Override
    public void deleteCompany(long id) {
        Company existingCompany = companyRepository.findById(id).orElse(null);
        if(existingCompany != null){
            boolean active = existingCompany.isActive();
            existingCompany.setActive(!active);
            companyRepository.save(existingCompany);
        }
    }

    @Override
    public Map<String, Object> getLast12MonthsData(Long companyId) {
        List<Object[]> data = companyDashBoardRepository.findLast12MonthsData(companyId);

        List<String> months = data.stream().map(row -> (String) row[0]).toList();
        List<Long> appliedJobs = data.stream().map(row -> (Long) row[1]).toList();
        List<Long> totalJobs = data.stream().map(row -> (Long) row[2]).toList();
        List<Long> appliedJobAccept = data.stream().map(row -> (Long) row[3]).toList();

        return Map.of(
                "months", months,
                "appliedJobs", appliedJobs,
                "totalJobs", totalJobs,
                "appliedJobAccept", appliedJobAccept
        );
    }

    @Override
    public boolean existByName(String name) {
        return companyRepository.existsByName(name);
    }

    @Override
    public CompanyImage createCompanyImage(Long companyId, CompanyImageDTO companyImageDTO) throws Exception {
        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Company with id = "
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

//    @Override
//    public Company createCompanyLogo(Long companyId, String url, String publicId) throws Exception {
//        Company existingCompany = companyRepository.findById(companyId)
//                .orElseThrow(() ->
//                        new DataNotFoundException("Cannot find Company with id = "
//                                +companyId));
//        existingCompany.setLogo(url);
//        existingCompany.setPublicIdImages(publicId);
//        return companyRepository.save(existingCompany);
//    }
}
