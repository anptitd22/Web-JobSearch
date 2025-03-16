package com.project.webIT.controllers;

import com.project.webIT.dtos.companies.CompanyDTO;
import com.project.webIT.dtos.companies.CompanyImageDTO;
import com.project.webIT.models.Company;
import com.project.webIT.models.CompanyImages;
import com.project.webIT.models.Job;
import com.project.webIT.response.companies.CompanyListResponse;
import com.project.webIT.response.companies.CompanyResponse;
import com.project.webIT.response.jobs.JobResponse;
import com.project.webIT.services.CloudinaryService;
import com.project.webIT.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "")
    public ResponseEntity<?> createCompany(
            @Valid @RequestBody CompanyDTO companyDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Company newCompany = companyService.createCompany(companyDTO);
            return ResponseEntity.ok().body(newCompany);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.endsWith(".jpg") || contentType.endsWith(".png") || contentType.endsWith(".webp"));
    }

    @PostMapping(value = "uploads/{companyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postImage(
            @PathVariable("companyId") Long companyId,
            @RequestPart("files") List<MultipartFile> files
    ){
        try{
            if (files == null) {
                files = new ArrayList<>(); //truong hop khong tai file
            }
            if(files.size() > 1){
                return ResponseEntity.badRequest().body("You can only upload maximum 1 images");
            }
            MultipartFile file = files.getFirst();
            if (file.getSize() == 0) { //truong hop file rong
                return ResponseEntity.badRequest().body("upload fail");
            }
            if (file.getSize() > 10 * 1024 * 1024) { //kiem tra kich thuoc file anh
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).
                        body("File is too large, Maximum is 10MB");
            }
            if (isImageFile(file)) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).
                        body("File must be an image");
            }
            Map image = cloudinaryService.upload(file);
            Object url = image.get("url");
            Object publicId = image.get("public_id");
            Company company = companyService.createCompanyLogo(companyId, url.toString(), publicId.toString());
            return ResponseEntity.ok().body(CompanyResponse.fromCompany(company));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@Valid @PathVariable Long id){
        try{
            Company existingCompany = companyService.getCompanyById(id);
            return ResponseEntity.ok().body(CompanyResponse.fromCompany(existingCompany));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{companyId}/jobs")
    public ResponseEntity<?> getJobs(@Valid @PathVariable("companyId") Long companyId){
        try{
            List<Job> jobs = companyService.getJobsByCompanyId(companyId);
            return ResponseEntity.ok().body(jobs.stream()
                    .map(JobResponse::fromJob)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<CompanyListResponse> getCompany(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name="industry_id") Long industryId,
            @RequestParam(defaultValue = "0", name= "page")   int page,
            @RequestParam(defaultValue = "20",name = "limit")  int limit
    ){
        //Tao Pageable tu thong tin trang va gioi han
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("name").descending());
        //Lay tong page
        Page<CompanyResponse> companyPage = companyService.getAllCompanies(keyword, industryId, pageRequest);
        int totalPages = companyPage.getTotalPages();
        List<CompanyResponse> companies = companyPage.getContent();
        System.out.println(companies);
        return ResponseEntity.ok(CompanyListResponse
                .builder()
                .companies(companies)
                .totalPages(totalPages)
                .totalCompanies(companyPage.getTotalElements())
                .build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateCompany(
            @Valid @PathVariable Long id,
            @Valid @RequestBody CompanyDTO companyDTO
    ){
        try{
            Company newcompany = companyService.updateCompany(id, companyDTO);
            return ResponseEntity.ok().body(CompanyResponse.fromCompany(newcompany));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    //xoa cung
    public ResponseEntity<String> deleteCompany(@Valid @PathVariable Long id){
        companyService.deleteCompany(id);
        return ResponseEntity.ok().body("delete Company successfully with id = "+id);
    }

    @DeleteMapping("close/{id}")
    @Transactional
    //xoa mem is_active -> 0
    public ResponseEntity<String> closeCompany(@Valid @PathVariable Long id){
        companyService.closeCompany(id);
        return ResponseEntity.ok().body("close Company successfully with id = "+id);
    }

    //    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadImages(
//            @PathVariable("id") Long companyId,
//            @ModelAttribute("files") ArrayList<MultipartFile> files
//    ){
//        try{
//            Company existingCompany = companyService.getCompanyById(companyId);
//            if (files == null) {
//                files = new ArrayList<>(); //truong hop khong tai file
//            }
//            if(files.size() > CompanyImages.MAXIMUM_IMAGES_PER_COMPANY){
//                return ResponseEntity.badRequest().body("You can only upload maximum 10 images");
//            }
//            List<CompanyImages> companyImages = new ArrayList<>();
//            for (MultipartFile file : files) {
//                if (file.getSize() == 0) { //truong hop file rong
//                    continue;
//                }
//                if (file.getSize() > 10 * 1024 * 1024) { //kiem tra kich thuoc file anh
//                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).
//                            body("File is too large, Maximum is 10MB");
//                }
//                String contentType = file.getContentType(); //kiem tra dinh dang file anh
//                if (contentType == null || !contentType.startsWith("image/")) {
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).
//                            body("File must be an image");
//                }
//                //Luu file va cap nhat duong dan trong DTO
//                String filename = storeFile(file); //Thay the ham
//                //Luu vao bang company_image
//                CompanyImages companyImage = companyService.createCompanyImage(existingCompany.getId(),
//                        CompanyImageDTO.builder()
//                                .imageUrl(filename)
//                                .build());
//                companyImages.add(companyImage);
////                existingCompany.setCompanyImages(companyImages);
//                //build sau
//            }
//            return ResponseEntity.ok().body("load successfully:"+"\n"+companyImages);
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    private String storeFile(MultipartFile file)throws IOException {
//        if (!isImageFile(file) || file.getOriginalFilename() == null){
//            throw new IOException("valid file format");
//        }
//        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())); //xoa ten file goc
//        //Them UUID vao truoc ten file de dam bao ten file la duy nhat khong de nhau
//        String uniqueFilename = UUID.randomUUID().toString()+"_"+filename;
//        //Duong dan den thu muc chua file
//        Path uploadDir = Paths.get("uploads");
//        //kiem tra va tao thu muc neu no khong ton tai
//        if(!Files.exists(uploadDir)){
//            Files.createDirectories(uploadDir);
//        }
//        //Duong dan day du den file
//        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
//        //Sao chep file vao thu muc chinh
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING); //sao chep neu ton tai thi thay the
//        return uniqueFilename;
//    }

//    @GetMapping("/images/{imageName}")
//    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
//        try{
//            java.nio.file.Path imagePath = Paths.get("Back-end/uploads/"+imageName);
//            UrlResource resource = new UrlResource(imagePath.toUri());
//            if(resource.exists()){
//                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
//                        .body(resource);
//            }else{
//                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
//                        .body(new UrlResource(Paths.get("Back-end/uploads/notfound404.jpg").toUri()));
//            }
//        }catch (Exception e){
//            return ResponseEntity.notFound().build();
//        }
//    }
}
