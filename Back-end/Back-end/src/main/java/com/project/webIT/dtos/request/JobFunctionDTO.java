package com.project.webIT.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobFunctionDTO {
    @NotEmpty(message = "JobFunction 's name cannot be empty")
    private String name;
}
