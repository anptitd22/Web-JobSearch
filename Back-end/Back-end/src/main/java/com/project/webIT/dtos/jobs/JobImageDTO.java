package com.project.webIT.dtos.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JobImageDTO {
    @JsonProperty("job_id")
    private Long jobId;

    @Size(min = 5, max = 300, message = "Image's name")
    @JsonProperty("image_url")
    private String imageUrl;
}
