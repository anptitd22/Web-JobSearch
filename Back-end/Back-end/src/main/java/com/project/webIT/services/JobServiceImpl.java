package com.project.webIT.services;

import com.project.webIT.constant.JobStatus;
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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements com.project.webIT.services.IService.JobService {
    private final JobRepository jobRepository;
    private final JobFunctionRepository jobFunctionRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final JobImageRepository jobImageRepository;
    private final NotificationService notificationService;
    private final UsersFavoriteCompaniesRepository usersFavoriteCompaniesRepository;
    private final UsersFavoriteJobsRepository usersFavoriteJobsRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;
    private final CompanyDashBoardRepository companyDashBoardRepository;

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
        newJob.setActive(newJob.getJobStatus().equals(JobStatus.Open));
        newJob.setView((long)0);

        var saved = jobRepository.save(newJob);

        if(!jobDTO.getJobStatus().equals(JobStatus.Open)){
            return saved;
        }

        updateTotalJob(jobDTO.getCompanyId());

        List<UserFavoriteCompany> followers = usersFavoriteCompaniesRepository.findByCompanyId(jobDTO.getCompanyId());
        if(!followers.isEmpty()){
            Notification notification = Notification.builder()
                    .status(NotificationStatus.CREATED)
                    .message(existingCompany.getName() + " đã đăng việc mới: "+ newJob.getName())
                    .jobTitle(newJob.getName())
                    .jobId(saved.getId())
                    .build();

            for(UserFavoriteCompany user : followers){
                Optional<User> existingUser = userRepository.findById(user.getUser().getId());
//                        .orElseThrow(() -> new DataNotFoundException("user id not found"));
                if(existingUser.isEmpty()){
                    continue;
                }
                UserNotification userNotification = new UserNotification();
                userNotification.setUser(existingUser.get());
                userNotification.setIsActive(true);
                userNotification.setJob(newJob);
                userNotification.setContent(existingCompany.getName() + " đã đăng việc mới: "+ newJob.getName());
                userNotification.setUserNotificationStatus(UserNotificationStatus.Unread);
                userNotificationRepository.save(userNotification);
            }

            List<String> userIds = followers.stream()
                    .map(follower -> follower.getUser().getId().toString())
                    .collect(Collectors.toList());
            notificationService.sendNotificationToUsers(userIds, notification);
        }
        return saved;
    }

    public void updateTotalJob(Long companyId) throws Exception {
        var existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new DataNotFoundException("company not found"));

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        Optional<CompanyDashBoard> existingRecord = companyDashBoardRepository.findByCompanyIdAndMonth(companyId, currentMonth);

        if (existingRecord.isPresent()) {
            // Nếu bản ghi đã tồn tại, cập nhật số lượng total_jobs
            CompanyDashBoard record = existingRecord.get();
            record.setTotalJobs(record.getTotalJobs() + 1);
            companyDashBoardRepository.save(record);
        } else {
            // Nếu bản ghi chưa tồn tại, tạo bản ghi mới
            CompanyDashBoard newRecord = new CompanyDashBoard();
            newRecord.setCompany(existingCompany);
            newRecord.setMonth(currentMonth);
            newRecord.setTotalJobs(1L);
            newRecord.setAppliedJobs(0L);
            newRecord.setAppliedJobAccept(0L);
            companyDashBoardRepository.save(newRecord);
        }
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
    public Page<JobResponse> getAllJobsFromAdmin(String keyword, Long jobFunctionId, JobStatus jobStatus, PageRequest pageRequest) {
        Page<Job> jobPage = jobRepository.searchJobsFromAdmin(jobFunctionId, keyword,jobStatus, pageRequest);
        return jobPage.map(JobResponse::fromJob);
    }

    @Override
    public List<Job> getAllJobsNotPage() {
        return jobRepository.findAll();
    }

    @Override
    public Job updateJob(long id, JobDTO jobDTO) throws Exception {
        boolean ok = false;
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("job is not found"));
        JobFunction existingJobFunction = jobFunctionRepository.findById(jobDTO.getJobFunctionId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Job Function with id = "
                                +jobDTO.getJobFunctionId()));
        Company existingCompany = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Company with id = "
                                +jobDTO.getCompanyId()));
        ModelMapper localModelMapper = new ModelMapper();
        if(!existingJob.getJobStatus().equals(JobStatus.Open) && !jobDTO.getJobStatus().equals(JobStatus.Open)){
            ok = true;
        }
        // Cấu hình mapper để bỏ qua các thuộc tính lồng nhau
        localModelMapper.getConfiguration()
                .setPropertyCondition(context -> {
                    if (context.getMapping().getLastDestinationProperty() != null) {
                        String path = context.getMapping().getPath();
                        return !path.startsWith("id") &&
                                !path.startsWith("jobFunction") &&
                                !path.startsWith("company");
                    }
                    return true;
                });

        localModelMapper.map(jobDTO, existingJob);
        existingJob.setActive(existingJob.getJobStatus().equals(JobStatus.Open));
        existingJob.setJobFunction(existingJobFunction);
        existingJob.setCompany(existingCompany);
//            existingJob.setUpdatedAt(LocalDateTime.now());

        var saved = jobRepository.save(existingJob);
        if(ok){
            return saved;
        }
        List<UserFavoriteCompany> followers = usersFavoriteCompaniesRepository.findByCompanyId(jobDTO.getCompanyId());
        if(!followers.isEmpty()){
            Notification notification = Notification.builder()
                    .status(NotificationStatus.UPDATED)
                    .message(existingCompany.getName() + " đã cập nhật công việc: "+ existingJob.getName())
                    .jobTitle(existingJob.getName())
                    .jobId(saved.getId())
                    .build();

            for(UserFavoriteCompany user : followers){
                Optional<User> existingUser = userRepository.findById(user.getUser().getId());
//                        .orElseThrow(() -> new DataNotFoundException("user id not found"));
                if(existingUser.isEmpty()){
                    continue;
                }
                UserNotification userNotification = new UserNotification();
                userNotification.setUser(existingUser.get());
                userNotification.setIsActive(true);
                userNotification.setJob(existingJob);
                userNotification.setContent(existingCompany.getName() + " đã cập nhật công việc: "+ existingJob.getName());
                userNotification.setUserNotificationStatus(UserNotificationStatus.Unread);
                userNotificationRepository.save(userNotification);
            }

            List<String> userIds = followers.stream()
                    .map(follower -> follower.getUser().getId().toString())
                    .collect(Collectors.toList());
            notificationService.sendNotificationToUsers(userIds, notification);
        }
        return saved;
    }

    @Override
    public void overJob(long id) throws Exception {
        Job existingJob = jobRepository.findById(id).orElse(null);
        if (existingJob != null){
            if(!existingJob.isActive()) {
                existingJob.setActive(true);
                existingJob.setJobStatus(JobStatus.Open);
                jobRepository.save(existingJob);
                return;
            }
            existingJob.setActive(false);
            existingJob.setJobStatus(JobStatus.Close);
            List<UserFavoriteJob> userFavoriteJobs = usersFavoriteJobsRepository.findByJobId(id);
            for (UserFavoriteJob userFavoriteJob: userFavoriteJobs){
                Notification notification = Notification.builder()
                        .status(NotificationStatus.END)
                        .message(userFavoriteJob.getJob().getName() + " đã bị kết thúc sớm vào " + LocalDateTime.now())
                        .jobTitle(userFavoriteJob.getJob().getName())
                        .jobId(userFavoriteJob.getJob().getId())
                        .build();

                Optional<User> existingUser = userRepository.findById(userFavoriteJob.getUser().getId());
//                        .orElseThrow(() -> new DataNotFoundException("user id not found"));
                if(existingUser.isEmpty()){
                    continue;
                }
                UserNotification userNotification = new UserNotification();
                userNotification.setUser(existingUser.get());
                userNotification.setIsActive(true);
                userNotification.setJob(existingJob);
                userNotification.setContent(userFavoriteJob.getJob().getName() + " đã bị kết thúc sớm vào " + LocalDateTime.now());
                userNotification.setUserNotificationStatus(UserNotificationStatus.Unread);
                userNotificationRepository.save(userNotification);

                notificationService.sendNotificationToUser(userFavoriteJob.getUser().getId().toString(),notification);
            }
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

    @Scheduled(cron = "0 */5 * * * *") // "giờ phút giây ngày tháng thứ"
    public void deactivateExpiredJobs() {
        List<Job> expiredJobs = jobRepository.findByEndAtBeforeAndIsActiveTrue(LocalDateTime.now());
        if (!expiredJobs.isEmpty()) {
            log.info("Tự động tắt {} job hết hạn", expiredJobs.size());
            for(Job job: expiredJobs){
                List<UserFavoriteJob> userFavoriteJobs = usersFavoriteJobsRepository.findByJobId(job.getId());
                for (UserFavoriteJob userFavoriteJob: userFavoriteJobs){
                    Notification notification = Notification.builder()
                            .status(NotificationStatus.END)
                            .message(userFavoriteJob.getJob().getName() + " đã kết thúc vào " + userFavoriteJob.getJob().getEndAt())
                            .jobTitle(userFavoriteJob.getJob().getName())
                            .jobId(userFavoriteJob.getJob().getId())
                            .build();

                    Optional<User> existingUser = userRepository.findById(userFavoriteJob.getUser().getId());
//                        .orElseThrow(() -> new DataNotFoundException("user id not found"));
                    if(existingUser.isEmpty()){
                        continue;
                    }
                    UserNotification userNotification = new UserNotification();
                    userNotification.setUser(existingUser.get());
                    userNotification.setIsActive(true);
                    userNotification.setJob(job);
                    userNotification.setContent(userFavoriteJob.getJob().getName() + " đã bị kết thúc sớm vào " + LocalDateTime.now());
                    userNotification.setUserNotificationStatus(UserNotificationStatus.Unread);
                    userNotificationRepository.save(userNotification);
                    notificationService.sendNotificationToUser(existingUser.get().getId().toString(),notification);
                }
                job.setJobStatus(JobStatus.Close);
                job.setActive(false);
            }
            jobRepository.saveAll(expiredJobs);
        }
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
