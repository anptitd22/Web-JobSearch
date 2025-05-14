package com.project.webIT.configurations;

import com.cloudinary.Cloudinary;
import com.project.webIT.components.CloudinaryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


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