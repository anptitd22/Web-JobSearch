package com.project.webIT.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "cloudinary")
@Component
public class CloudinaryProperties {
    private String cloudName;
    private String apiKey;
    private String apiSecret;
}
