package com.project.webIT.services;

import com.project.webIT.dtos.request.UserCVDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.User;
import com.project.webIT.models.UserCV;
import com.project.webIT.repositories.UserCvRepository;
import com.project.webIT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCVServiceImpl {
    private final UserCvRepository userCvRepository;
    private final UserRepository userRepository;

    public UserCV createCV (Long userId, UserCVDTO userCVDTO) throws Exception{
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("cannot found user"));
        UserCV userCV = new UserCV();
        userCV.setUser(existingUser);
        userCV.setCreatedAt(LocalDateTime.now());
        userCV.setUpdatedAt(LocalDateTime.now());
        userCV.setName(userCVDTO.getName());
        userCV.setCvUrl(userCVDTO.getCvUrl());
        userCV.setPublicIdCv(userCVDTO.getPublicIdCV());
        userCV.setIsActive(true);
        return userCvRepository.save(userCV);
    }

    public List<UserCV> getCVs(Long userId){
        return userCvRepository.findByUserIdAndIsActiveTrueOrderByUpdatedAtDesc(userId);
    }

    public String getPublicIdCV(Long id) throws Exception{
        UserCV userCV = userCvRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot found userCV"));
        userCV.setIsActive(false);
        userCvRepository.save(userCV);
        return userCV.getPublicIdCv();
    }

    public UserCV updateUserCv(Long id, String name) throws  Exception{
        UserCV existingUserCV = userCvRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot found userCV"));
        existingUserCV.setName(name);
        return userCvRepository.save(existingUserCV);
    }
}
