package com.project.webIT.services;

import com.project.webIT.dtos.request.UserPaymentDTO;
import com.project.webIT.dtos.response.CompanyResponse;
import com.project.webIT.dtos.response.UserPaymentResponse;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.*;
import com.project.webIT.repositories.AdminDashboardRevenueRepository;
import com.project.webIT.repositories.AdminRepository;
import com.project.webIT.repositories.UserPaymentRepository;
import com.project.webIT.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserPaymentServiceImpl {

    private final UserRepository userRepository;
    private final UserPaymentRepository userPaymentRepository;
    private final ModelMapper modelMapper;
    private final AdminDashboardRevenueRepository adminDashboardRevenueRepository;
    private final AdminRepository adminRepository;

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

        updateTotalRevenue(userPaymentDTO.getUserId(), userPaymentDTO.getAmount());

        return userPaymentRepository.save(userPayment);
    }

    public void updateTotalRevenue(Long userId, Double amount) throws Exception {
        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        var existingAdmin = adminRepository.findById(1L)
                .orElseThrow(() -> new DataNotFoundException("admin not found"));

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Optional<AdminDashboardRevenue> existingRecord = adminDashboardRevenueRepository.findByAdminIdAndMonth(1L, currentMonth);

        if (existingRecord.isPresent()) {
            // Nếu bản ghi đã tồn tại, cập nhật số lượng total_jobs
            AdminDashboardRevenue record = existingRecord.get();
            record.setTotalRevenue(record.getTotalRevenue() + amount);
            adminDashboardRevenueRepository.save(record);
        } else {
            // Nếu bản ghi chưa tồn tại, tạo bản ghi mới
            AdminDashboardRevenue newRecord = new AdminDashboardRevenue();
            newRecord.setAdmin(existingAdmin);
            newRecord.setMonth(currentMonth);
            newRecord.setTotalRevenue(amount);
            adminDashboardRevenueRepository.save(newRecord);
        }
    }

    public List<UserPayment> findByUserId (Long userId){
        return userPaymentRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    public Page<UserPaymentResponse> managerPayment (String keyword, String status, PageRequest pageRequest) {
        Page<UserPayment> userPayments = userPaymentRepository.managerPayment(keyword, status, pageRequest);
        return userPayments.map(UserPaymentResponse::fromUserPayment);
    }
}
