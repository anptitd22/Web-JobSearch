package com.project.webIT;

import com.cloudinary.Cloudinary;
import com.project.webIT.properties.CloudinaryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CloudinaryProperties.class})
public class WebItApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebItApplication.class, args);
	}

}
