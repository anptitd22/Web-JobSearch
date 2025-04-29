package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRecommendDTO {
    @JsonProperty("currentJob")
    private String currentJob;

    @JsonProperty("note")
    private String note;

    @JsonProperty("currentJobFunction")
    private int currentJobFunction;

    private String address;
}
