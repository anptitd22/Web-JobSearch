package com.project.webIT.services;

import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.User;
import com.project.webIT.models.UserDashboard;
import com.project.webIT.repositories.UserDashboardRepository;
import com.project.webIT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDashboardService implements com.project.webIT.services.IService.UserDashboardService {

    private final UserDashboardRepository userDashboardRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> getLast12MonthsData(Long userId) {
        List<Object[]> data = userDashboardRepository.findLast12MonthsData(userId);

        List<String> months = data.stream().map(row -> (String) row[0]).toList();
        List<Long> appliedJobs = data.stream().map(row -> (Long) row[1]).toList();
        List<Long> jobViews = data.stream().map(row -> (Long) row[2]).toList();
        List<Long> jobSearches = data.stream().map(row -> (Long) row[3]).toList();

        return Map.of(
                "months", months,
                "appliedJobs", appliedJobs,
                "jobViews", jobViews,
                "jobSearches", jobSearches
        );
    }

    @Override
    public UserDashboard updateAppliedJobs(Long userId) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Optional<UserDashboard> existingRecord = userDashboardRepository.findByUserIdAndMonth(userId, currentMonth);

        if (existingRecord.isPresent()) {
            // Nếu bản ghi đã tồn tại, cập nhật số lượng applied_jobs
            UserDashboard record = existingRecord.get();
            record.setAppliedJobs(record.getAppliedJobs() + 1);
            userDashboardRepository.save(record);
        } else {
            // Nếu bản ghi chưa tồn tại, tạo bản ghi mới
            UserDashboard newRecord = new UserDashboard();
            newRecord.setUser(existingUser);
            newRecord.setMonth(currentMonth);
            newRecord.setAppliedJobs(1L);
            newRecord.setJobViews(0L);
            newRecord.setJobSearches(0L);
            userDashboardRepository.save(newRecord);
        }
        return null;
    }

    @Override
    public UserDashboard updateJobViews(Long userId) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Optional<UserDashboard> existingRecord = userDashboardRepository.findByUserIdAndMonth(userId, currentMonth);

        if (existingRecord.isPresent()) {
            // Nếu bản ghi đã tồn tại, cập nhật số lượng job_views
            UserDashboard record = existingRecord.get();
            record.setJobViews(record.getJobViews() + 1);
            userDashboardRepository.save(record);
        } else {
            // Nếu bản ghi chưa tồn tại, tạo bản ghi mới
            UserDashboard newRecord = new UserDashboard();
            newRecord.setUser(existingUser);
            newRecord.setMonth(currentMonth);
            newRecord.setAppliedJobs(0L);
            newRecord.setJobViews(1L);
            newRecord.setJobSearches(0L);
            userDashboardRepository.save(newRecord);
        }
        return null;
    }

    @Override
    public UserDashboard updateJobSearches(Long userId) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Optional<UserDashboard> existingRecord = userDashboardRepository.findByUserIdAndMonth(userId, currentMonth);

        if (existingRecord.isPresent()) {
            // Nếu bản ghi đã tồn tại, cập nhật số lượng job_searches
            UserDashboard record = existingRecord.get();
            record.setJobSearches(record.getJobSearches() + 1);
            userDashboardRepository.save(record);
        } else {
            // Nếu bản ghi chưa tồn tại, tạo bản ghi mới
            UserDashboard newRecord = new UserDashboard();
            newRecord.setUser(existingUser);
            newRecord.setMonth(currentMonth);
            newRecord.setAppliedJobs(0L);
            newRecord.setJobViews(0L);
            newRecord.setJobSearches(1L);
            userDashboardRepository.save(newRecord);
        }
        return null;
    }
}
