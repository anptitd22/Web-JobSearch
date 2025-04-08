package com.project.webIT.services;

import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.Company;
import com.project.webIT.models.User;
import com.project.webIT.models.UsersFavoriteCompanies;
import com.project.webIT.repositories.CompanyRepository;
import com.project.webIT.repositories.UserRepository;
import com.project.webIT.repositories.UsersFavoriteCompaniesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersFavoriteCompaniesService implements com.project.webIT.services.IService.UsersFavoriteCompaniesService {

    private final UsersFavoriteCompaniesRepository usersFavoriteCompaniesRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UsersFavoriteCompanies saveFavoriteCompany(Long userId, Long companyId) throws Exception {
        Optional<UsersFavoriteCompanies> favoriteCompanies = usersFavoriteCompaniesRepository.findByUserIdAndCompanyId(userId, companyId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new DataNotFoundException("company not found"));

        if(favoriteCompanies.isPresent()){
            UsersFavoriteCompanies usersFavoriteCompanies = favoriteCompanies.get();
            usersFavoriteCompanies.setActive(!usersFavoriteCompanies.isActive());
            usersFavoriteCompanies.setUpdatedAt(LocalDateTime.now());
            return usersFavoriteCompaniesRepository.save(usersFavoriteCompanies);
        }else{
            UsersFavoriteCompanies newUsersFavoriteCompanies = new UsersFavoriteCompanies();
            newUsersFavoriteCompanies.setUser(existingUser);
            newUsersFavoriteCompanies.setCompany(existingCompany);
            newUsersFavoriteCompanies.setActive(true);
            return usersFavoriteCompaniesRepository.save(newUsersFavoriteCompanies);
        }
    }

    @Override
    public List<UsersFavoriteCompanies> getUserFavorites(Long userId) {
        return usersFavoriteCompaniesRepository.findByUserId(userId);
    }
}
