package com.project.webIT.controllers;

import com.project.webIT.models.UsersFavoriteCompanies;
import com.project.webIT.models.UsersFavoriteJobs;
import com.project.webIT.response.users.UsersFavoriteCompaniesResponse;
import com.project.webIT.response.users.UsersFavoriteJobsResponse;
import com.project.webIT.services.UsersFavoriteCompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/my-companies")
@RequiredArgsConstructor
public class UserFavoriteCompaniesController {
    private final UsersFavoriteCompaniesService usersFavoriteCompaniesService;

    @PostMapping("{userId}/{companyId}")
    public ResponseEntity<?> saveFavorite(
            @PathVariable("userId") Long userId,
            @PathVariable("companyId") Long companyId
    ) {
        try{
            UsersFavoriteCompanies usersFavoriteCompanies = usersFavoriteCompaniesService.saveFavoriteCompany(userId, companyId);
            return ResponseEntity.ok(usersFavoriteCompanies);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<UsersFavoriteCompaniesResponse>> getFavorites(@PathVariable("userId") Long userId){
        List<UsersFavoriteCompanies> usersFavoriteCompanies = usersFavoriteCompaniesService.getUserFavorites(userId);
        List<UsersFavoriteCompaniesResponse> responseList = usersFavoriteCompanies.stream()
                .map(UsersFavoriteCompaniesResponse::fromUserFavoriteCompanies)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
}
