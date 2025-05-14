package com.project.webIT.services;

import com.project.webIT.constant.AppliedJobStatus;
import com.project.webIT.constant.NotificationStatus;
import com.project.webIT.constant.UserNotificationStatus;
import com.project.webIT.dtos.request.CompanyFeedbackAcceptDTO;
import com.project.webIT.dtos.request.CompanyFeedbackRefuseDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.*;
import com.project.webIT.notification.Notification;
import com.project.webIT.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyFeedbackServiceImpl implements com.project.webIT.services.IService.CompanyFeedbackService {
    private final CompanyFeedbackAcceptRepository companyFeedbackAcceptRepository;
    private final CompanyRepository companyRepository;
    private final AppliedJobRepository appliedJobRepository;
    private final CompanyFeedbackRefuseRepository companyFeedbackRefuseRepository;
    private final NotificationService notificationService;
    private final UserNotificationRepository userNotificationRepository;
    private final CompanyDashBoardRepository companyDashBoardRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompanyFeedbackAccept createFeedbackAccept(CompanyFeedbackAcceptDTO companyFeedbackAcceptDTO) throws Exception {
        Company existingCompany = companyRepository.findById(companyFeedbackAcceptDTO.getCompanyId())
                .orElseThrow(() -> new DataNotFoundException("company is not found"));
        AppliedJob existingAppliedJob = appliedJobRepository.findById(companyFeedbackAcceptDTO.getAppliedJobId())
                .orElseThrow(() -> new DataNotFoundException("applied job is not found"));

        modelMapper.typeMap(CompanyFeedbackAcceptDTO.class, CompanyFeedbackAccept.class)
                .addMappings(mapper ->
                        mapper.skip(CompanyFeedbackAccept::setId));
        CompanyFeedbackAccept companyFeedbackAccept = new CompanyFeedbackAccept();
        modelMapper.map(companyFeedbackAcceptDTO, companyFeedbackAccept);
        companyFeedbackAccept.setIsActive(true);
        companyFeedbackAccept.setCompany(existingCompany);
        companyFeedbackAccept.setAppliedJob(existingAppliedJob);

        existingAppliedJob.setFeedBackDate(LocalDateTime.now());
        existingAppliedJob.setAppliedJobStatus(AppliedJobStatus.Accept);
        existingAppliedJob.setActive(false);

        Notification notification = Notification.builder()
                .status(NotificationStatus.CREATED)
                .message(existingCompany.getName()+ "phản hồi ứng tuyển của bạn về công việc:"
                        + existingAppliedJob.getJob().getName())
                .jobId(existingAppliedJob.getJob().getId())
                .build();

        updateAppliedJobAccept(companyFeedbackAcceptDTO.getCompanyId());

        notificationService.sendNotificationToUser(existingAppliedJob.getUser().getId().toString(), notification);

        appliedJobRepository.save(existingAppliedJob);

        UserNotification userNotification = new UserNotification();
        userNotification.setUser(existingAppliedJob.getUser());
        userNotification.setIsActive(true);
        userNotification.setJob(existingAppliedJob.getJob());
        userNotification.setContent(existingCompany.getName() + " đã phản hồi : "+ existingAppliedJob.getJob().getName());
        userNotification.setUserNotificationStatus(UserNotificationStatus.Unread);

        userNotificationRepository.save(userNotification);

        return companyFeedbackAcceptRepository.save(companyFeedbackAccept);
    }

    public void updateAppliedJobAccept(Long companyId) throws Exception {
        var existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new DataNotFoundException("company not found"));

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Optional<CompanyDashBoard> existingRecord = companyDashBoardRepository.findByCompanyIdAndMonth(companyId, currentMonth);

        if (existingRecord.isPresent()) {
            // Nếu bản ghi đã tồn tại, cập nhật số lượng total_jobs
            CompanyDashBoard record = existingRecord.get();
            record.setAppliedJobAccept(record.getAppliedJobAccept() + 1);
            companyDashBoardRepository.save(record);
        } else {
            // Nếu bản ghi chưa tồn tại, tạo bản ghi mới
            CompanyDashBoard newRecord = new CompanyDashBoard();
            newRecord.setCompany(existingCompany);
            newRecord.setMonth(currentMonth);
            newRecord.setTotalJobs(0L);
            newRecord.setAppliedJobs(0L);
            newRecord.setAppliedJobAccept(1L);
            companyDashBoardRepository.save(newRecord);
        }
    }

    @Override
    public CompanyFeedbackAccept getFeedbackAccept(Long id) throws Exception {
        var existingFeedback = appliedJobRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("applied job not found"));
        return companyFeedbackAcceptRepository.findByAppliedJobId(id);
    }

    @Override
    public CompanyFeedbackRefuse createFeedbackRefuse(CompanyFeedbackRefuseDTO companyFeedbackRefuseDTO) throws Exception {
        Company existingCompany = companyRepository.findById(companyFeedbackRefuseDTO.getCompanyId())
                .orElseThrow(() -> new DataNotFoundException("company is not found"));
        AppliedJob existingAppliedJob = appliedJobRepository.findById(companyFeedbackRefuseDTO.getAppliedJobId())
                .orElseThrow(() -> new DataNotFoundException("applied job is not found"));

        modelMapper.typeMap(CompanyFeedbackRefuseDTO.class, CompanyFeedbackRefuse.class)
                .addMappings(mapper ->
                        mapper.skip(CompanyFeedbackRefuse::setId));
        CompanyFeedbackRefuse companyFeedbackRefuse = new CompanyFeedbackRefuse();
        modelMapper.map(companyFeedbackRefuseDTO, companyFeedbackRefuse);
        companyFeedbackRefuse.setIsActive(true);
        companyFeedbackRefuse.setCompany(existingCompany);
        companyFeedbackRefuse.setAppliedJob(existingAppliedJob);

        existingAppliedJob.setFeedBackDate(LocalDateTime.now());
        existingAppliedJob.setAppliedJobStatus(AppliedJobStatus.Refuse);
        existingAppliedJob.setActive(false);

        Notification notification = Notification.builder()
                .status(NotificationStatus.CREATED)
                .message(existingCompany.getName()+ "phản hồi ứng tuyển của bạn về công việc:"
                        + existingAppliedJob.getJob().getName())
                .jobId(existingAppliedJob.getJob().getId())
                .build();
        notificationService.sendNotificationToUser(existingAppliedJob.getUser().getId().toString(), notification);
        appliedJobRepository.save(existingAppliedJob);

        UserNotification userNotification = new UserNotification();
        userNotification.setUser(existingAppliedJob.getUser());
        userNotification.setIsActive(true);
        userNotification.setJob(existingAppliedJob.getJob());
        userNotification.setContent(existingCompany.getName() + " đã phản hồi : "+ existingAppliedJob.getJob().getName());
        userNotification.setUserNotificationStatus(UserNotificationStatus.Unread);
        userNotificationRepository.save(userNotification);

        return companyFeedbackRefuseRepository.save(companyFeedbackRefuse);
    }

    @Override
    public CompanyFeedbackRefuse getFeedbackRefuse(Long id) throws Exception {
        var existingFeedback = appliedJobRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("applied job not found"));
        return companyFeedbackRefuseRepository.findByAppliedJobId(id);
    }
}
