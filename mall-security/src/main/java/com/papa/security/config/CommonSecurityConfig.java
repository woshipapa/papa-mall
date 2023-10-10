package com.papa.security.config;

import com.papa.security.component.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonSecurityConfig {

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig(){
        return new IgnoreUrlsConfig();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}


    @Bean
    public jwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new jwtAuthenticationTokenFilter();
    }


    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }


    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource(){
        return new DynamicSecurityMetadataSource();
    }

    @Bean
    @ConditionalOnBean(name="dynamicSecurityService")
    public DynamicSecurityFilter dynamicSecurityFilter(){
        return new DynamicSecurityFilter();
    }


    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicAccessDecisionManager dynamicAccessDecisionManager(){
        return new DynamicAccessDecisionManager();
    }





}
