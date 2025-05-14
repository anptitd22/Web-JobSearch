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
public class DataListResponse<T>{
    private List<T> dataList;
    private Integer totalPages;
    private Long totalData;
}
