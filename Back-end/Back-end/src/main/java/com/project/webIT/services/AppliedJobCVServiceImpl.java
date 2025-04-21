package com.project.webIT.services;

import com.project.webIT.dtos.request.AppliedJobCVDTO;
import com.project.webIT.dtos.request.AppliedJobDTO;
import com.project.webIT.exceptions.DataNotFoundException;
import com.project.webIT.models.AppliedJob;
import com.project.webIT.models.AppliedJobCV;
import com.project.webIT.models.UserCV;
import com.project.webIT.repositories.AppliedJobCVRepository;
import com.project.webIT.repositories.AppliedJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppliedJobCVServiceImpl {
    private final AppliedJobCVRepository appliedJobCVRepository;
    private final AppliedJobRepository appliedJobRepository;

    public AppliedJobCV createAppliedJobCV(AppliedJobCVDTO appliedJobCVDTO)throws Exception{
        var existingAppliedJob = appliedJobRepository.findById(appliedJobCVDTO.getAppliedJobId())
                .orElseThrow(() -> new DataNotFoundException("applied job not found"));
        var appliedJobCV = new AppliedJobCV();
        appliedJobCV.setAppliedJob(existingAppliedJob);
        appliedJobCV.setName(appliedJobCVDTO.getName());
        appliedJobCV.setCvUrl(appliedJobCVDTO.getCvUrl());
        appliedJobCV.setPublicIdCV(appliedJobCVDTO.getPublicIdCV());
        appliedJobCV.setIsActive(true);
        return appliedJobCVRepository.save(appliedJobCV);
    }

    public List<AppliedJobCV> getAppliedJobCV(Long appliedJobId){
        return appliedJobCVRepository.findByAppliedJobIdAndIsActiveTrueOrderByUpdatedAtDesc(appliedJobId);
    }

    public String getPublicIdCV(Long id) throws Exception{
        var existingAppliedJobCV = appliedJobCVRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot found appliedJobCV"));
        existingAppliedJobCV.setIsActive(false);
        appliedJobCVRepository.save(existingAppliedJobCV);
        return existingAppliedJobCV.getPublicIdCV();
    }

    public AppliedJobCV updateAppliedJobCV(Long id, String name) throws  Exception{
        var existingAppliedJobCV = appliedJobCVRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot found appliedJobCV"));
        existingAppliedJobCV.setName(name);
        return appliedJobCVRepository.save(existingAppliedJobCV);
    }
}
