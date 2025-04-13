package com.project.webIT.services;

import com.project.webIT.dtos.request.UserPaymentDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.UserPayment;
import com.project.webIT.models.User;
import com.project.webIT.repositories.UserPaymentRepository;
import com.project.webIT.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserPaymentServiceImpl {
    private final UserRepository userRepository;
    private final UserPaymentRepository userPaymentRepository;
    private final ModelMapper modelMapper;

    public UserPayment createBill(UserPaymentDTO userPaymentDTO) throws  Exception{
        User existingUser = userRepository.findById(userPaymentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("not found user"));
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(UserPaymentDTO.class, UserPayment.class)
                .addMappings(mapper ->
                        mapper.skip(UserPayment::setId));
        UserPayment userPayment = new UserPayment();
        modelMapper.map(userPaymentDTO, userPayment);
        String vietnameseStatus = UserPayment.STATUS_MAP.getOrDefault(userPaymentDTO.getStatus(), "Không xác định");
        userPayment.setUser(existingUser);
        userPayment.setStatus(vietnameseStatus);
        userPayment.setCreatedAt(LocalDateTime.now());
        return userPaymentRepository.save(userPayment);
    }
}
