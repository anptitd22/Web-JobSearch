package com.project.webIT.services.IService;

import com.project.webIT.models.Industry;

import java.util.List;

public interface IIndustryService {
//    JobFunction createJobFunction(JobFunctionDTO jobFunctionDTO);

    Industry getIndustryById(long id);

    List<Industry> getAllIndustries();
}
