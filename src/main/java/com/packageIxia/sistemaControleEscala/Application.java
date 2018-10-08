package com.packageIxia.sistemaControleEscala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.packageIxia.sistemaControleEscala.services.referencias.StorageProperties;

@Configuration
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application implements WebMvcConfigurer  {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
      // LogInterceptor apply to all URLs.
		registry.addInterceptor(new AuthorizatorInterceptor());
 
//      // This interceptor apply to URL like /admin/*
//      // Exclude /admin/oldLogin
//      registry.addInterceptor(new AdminInterceptor())//
//            .addPathPatterns("/admin/*")//
//            .excludePathPatterns("/admin/oldLogin");
	}

    /*@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }*/
}
