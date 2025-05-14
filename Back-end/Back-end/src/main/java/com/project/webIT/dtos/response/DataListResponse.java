package com.project.webIT.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ObjectListResponse <T>{
    private List<T> objectList;
    private int totalPages;
    private long totalCompanies;
}
