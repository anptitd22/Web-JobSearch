package com.project.webIT.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.District;
import com.project.webIT.models.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProvinceResponse {
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("districts")
    private List<District> districts;

    public static ProvinceResponse fromProvince(Province province){
        return ProvinceResponse.builder()
                .id(province.getId())
                .name(province.getName())
                .districts(province.getDistricts())
                .build();
    }
}
