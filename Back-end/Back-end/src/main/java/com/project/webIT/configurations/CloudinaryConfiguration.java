package com.project.webIT.configurations;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.project.webIT.properties.CloudinaryProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Configuration
@RequiredArgsConstructor
public class CloudinaryConfiguration {

    private final CloudinaryProperties cloudinaryProperties;


    @Bean
    public Cloudinary cloudinary(){
        final Map<String, String> config = new HashMap<String,String>();
        config.put("cloud_name", cloudinaryProperties.getCloudName());
        config.put("api_key", cloudinaryProperties.getApiKey());
        config.put("api_secret", cloudinaryProperties.getApiSecret());
        return new Cloudinary(config);
    }

}