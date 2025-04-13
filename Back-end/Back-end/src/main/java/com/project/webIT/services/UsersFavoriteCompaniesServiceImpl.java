package com.project.webIT.services;

import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.User;
import com.project.webIT.models.UserFavoriteCompany;
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
public class UsersFavoriteCompaniesServiceImpl implements com.project.webIT.services.IService.UsersFavoriteCompaniesService {

    private final UsersFavoriteCompaniesRepository usersFavoriteCompaniesRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserFavoriteCompany saveFavoriteCompany(User user, Long companyId) throws Exception {
        Optional<UserFavoriteCompany> favoriteCompanies = usersFavoriteCompaniesRepository.findByUserIdAndCompanyId(user.getId(), companyId);

        var existingCompanyEntity = companyRepository.findById(companyId)
                .orElseThrow(() -> new DataNotFoundException("company not found"));

        if(favoriteCompanies.isPresent()){
            UserFavoriteCompany userFavoriteCompany = favoriteCompanies.get();
            userFavoriteCompany.setActive(!userFavoriteCompany.isActive());
            userFavoriteCompany.setUpdatedAt(LocalDateTime.now());
            return usersFavoriteCompaniesRepository.save(userFavoriteCompany);
        }else{
            var newUserFavoriteCompanyEntity = new UserFavoriteCompany();
            newUserFavoriteCompanyEntity.setUser(user);
            newUserFavoriteCompanyEntity.setCompany(existingCompanyEntity);
            newUserFavoriteCompanyEntity.setActive(true);
            return usersFavoriteCompaniesRepository.save(newUserFavoriteCompanyEntity);
        }
    }

    @Override
    public List<UserFavoriteCompany> getUserFavorites(Long userId) {
        return usersFavoriteCompaniesRepository.findByUserId(userId);
    }
}
