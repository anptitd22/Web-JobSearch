package com.project.webIT.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webIT.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
