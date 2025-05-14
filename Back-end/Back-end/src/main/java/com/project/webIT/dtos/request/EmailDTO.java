package com.project.webIT.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    @JsonProperty("current_password")
    private String currentPassword;

    @JsonProperty("new_email")
    private String newEmail;
}
