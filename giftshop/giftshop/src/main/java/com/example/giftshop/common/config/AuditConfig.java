package com.example.giftshop.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //JPA Auditing 기능
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){ //AuditorAware 빈 등록
        return new AuditorAwareImpl();
    }
}
