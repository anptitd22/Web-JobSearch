package com.project.webIT.services;

import com.project.webIT.constant.NotificationStatus;
import com.project.webIT.constant.UserNotificationStatus;
import com.project.webIT.dtos.request.JobDTO;
import com.project.webIT.dtos.request.JobImageDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.exceptions.InvalidParamException;
import com.project.webIT.models.*;
import com.project.webIT.notification.Notification;
import com.project.webIT.repositories.*;
import com.project.webIT.dtos.response.JobResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements com.project.webIT.services.IService.JobService {
    private final JobRepository jobRepository;
    private final JobFunctionRepository jobFunctionRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final JobImageRepository jobImageRepository;
    private final NotificationService notificationService;
    private final UsersFavoriteCompaniesRepository usersFavoriteCompaniesRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;

    @Override
    public Job createJob(JobDTO jobDTO) throws Exception {
        JobFunction existingJobFunction = jobFunctionRepository.findById(jobDTO.getJobFunctionId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job Function with id = "
                                +jobDTO.getJobFunctionId()));
        Company existingCompany = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Company with id = "
                                +jobDTO.getCompanyId()));
        modelMapper.typeMap(JobDTO.class, Job.class)
                .addMappings(mapper ->
                        mapper.skip(Job::setId));
        Job newJob = new Job();
        modelMapper.map(jobDTO, newJob);
        newJob.setJobFunction(existingJobFunction);
        newJob.setCompany(existingCompany);
        newJob.setActive(true);
        newJob.setView((long)0);


//        notificationService.sendNotification(
//                LocalDateTime.now().toString(),
//                Notification.builder()
//                        .status(NotificationStatus.BORROWED)
//                        .message("has new job")
//                        .jobTitle(newJob.getName())
//                        .build()
//        );
        var saved = jobRepository.save(newJob);
        List<UserFavoriteCompany> followers = usersFavoriteCompaniesRepository.findByCompanyId(jobDTO.getCompanyId());
        if(!followers.isEmpty()){
            Notification notification = Notification.builder()
                    .status(NotificationStatus.CREATED)
                    .message(existingCompany.getName() + " đã đăng việc mới: "+ newJob.getName())
                    .jobTitle(newJob.getName())
                    .jobId(saved.getId())
                    .build();

            for(UserFavoriteCompany user : followers){
                User existingUser = userRepository.findById(user.getUser().getId())
                        .orElseThrow(() -> new DataNotFoundException("user id not found"));

                UserNotification userNotification = new UserNotification();
                userNotification.setUser(existingUser);
                userNotification.setIsActive(true);
                userNotification.setJob(newJob);
                userNotification.setUserNotificationStatus(UserNotificationStatus.Unread);
                userNotificationRepository.save(userNotification);
            }

            List<String> userIds = followers.stream()
                    .map(follower -> follower.getUser().getId().toString())
                    .collect(Collectors.toList());
            notificationService.sendNotificationToUsers(userIds, notification);
        }
//        notificationService.broadcastNotification(
//                Notification.builder()
//                        .status(NotificationStatus.CREATED)
//                        .message("has new job")
//                        .jobTitle(newJob.getName()).build()
//        );
        return saved;
    }

    @Override
    public Job getJobById(long id) throws Exception {
        Job job =  jobRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job with id = "+id));
        job.setView(job.getView()+1);
        return jobRepository.save(job);
    }

    @Override
    public Job getJobByIdFromCompany(Long id) throws Exception {
        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job with id = "+id));
    }

    @Override
    public Page<JobResponse> getAllJobs(String keyword, Long jobFunctionId, PageRequest pageRequest) { //page va limit
        //lay danh sach cong viec theo trang(page) va gioi han(limit)
        Page<Job> jobPage = jobRepository.searchJobs(jobFunctionId, keyword, pageRequest);
        return jobPage.map(JobResponse::fromJob);
    }

    @Override
    public List<Job> getAllJobsNotPage() {
        return jobRepository.findAll();
    }

    @Override
    public Job updateJob(long id, JobDTO jobDTO) throws Exception {
        Job existingJob = getJobById(id);
        if(existingJob != null){
            JobFunction existingJobFunction = jobFunctionRepository.findById(jobDTO.getJobFunctionId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find Job Function with id = "
                                    +jobDTO.getJobFunctionId()));
            Company existingCompany = companyRepository.findById(jobDTO.getCompanyId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find Company with id = "
                                    +jobDTO.getCompanyId()));
            modelMapper.typeMap(JobDTO.class, Job.class)
                    .addMappings(mapper ->
                            mapper.skip(Job::setId));
            modelMapper.map(jobDTO, existingJob);
            existingJob.setJobFunction(existingJobFunction);
            existingJob.setCompany(existingCompany);
//            existingJob.setUpdatedAt(LocalDateTime.now());
            return jobRepository.save(existingJob);
        }
        return null;
    }

    @Override
    public void overJob(long id) throws Exception {
        Job existingJob = jobRepository.findById(id).orElse(null);
        if (existingJob != null){
            boolean active = existingJob.isActive();
            existingJob.setActive(!active);
            jobRepository.save(existingJob);
        }
    }

    @Override
    public void deleteJob(long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if(optionalJob.isPresent()) {
            jobRepository.deleteById(id);
        }
    }

    @Override
    public boolean existByName(String name) {
        return jobRepository.existsByName(name);
    }

    @Override
    public JobImage createJobImage(Long jobId, JobImageDTO jobImageDTO) throws Exception {
        Job existingJob = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job Function with id = "
                                +jobImageDTO.getJobId()));
        JobImage newJobImage = JobImage.builder()
                .job(existingJob)
                .imageUrl(jobImageDTO.getImageUrl())
                .build();
        //khong cho insert qua 5 anh
        int size = jobImageRepository.findByJobId(jobId).size();
        if(size >= JobImage.MAXIMUM_IMAGES_PER_JOB){
            throw new InvalidParamException("Number of Image must be <= " +
                    JobImage.MAXIMUM_IMAGES_PER_JOB);
        }
        return jobImageRepository.save(newJobImage);
    }

    @Override
    public Job increaseViews(Long jobId, Authentication authentication) throws Exception {
        Job job = jobRepository.findById(jobId)
                .orElseThrow( () -> new DataNotFoundException("job not found"));
        String currentUsername = authentication.getName();
        if (job.getCompany().getName().equals(currentUsername)) {
            throw new InvalidParamException("Bạn không thể tự tăng lượt xem sản phẩm của mình");
        }
        job.setView(job.getView() + 1);
        return jobRepository.save(job);
    }
}
