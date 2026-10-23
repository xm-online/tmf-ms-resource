package com.icthh.xm.tmf.ms.resource.config;

import com.icthh.xm.commons.permission.access.XmPermissionEvaluator;
import com.icthh.xm.commons.security.jwt.TokenProvider;
import com.icthh.xm.commons.security.spring.config.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class MicroserviceSecurityConfiguration extends SecurityConfiguration {

    public MicroserviceSecurityConfiguration(TokenProvider tokenProvider,
                                             @Value("${jhipster.security.content-security-policy}")
                                             String contentSecurityPolicy) {
        super(tokenProvider, contentSecurityPolicy);
    }

    /**
     * The xm-commons rules plus the fallback the service had before the migration: the Spring Security OAuth2
     * resource server permitted requests matched by no rule (the v4 TMF API under /tmf-api is not under /api),
     * while Spring Security 6 denies them. Tightening this is a behaviour change and needs a separate decision.
     */
    @Override
    protected HttpSecurity applyUrlSecurity(HttpSecurity http) {
        HttpSecurity configured = super.applyUrlSecurity(http);
        try {
            return configured.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        } catch (Exception e) {
            throw new IllegalStateException("Cannot configure URL security", e);
        }
    }

    @Primary
    @Bean
    static MethodSecurityExpressionHandler expressionHandler(XmPermissionEvaluator customPermissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
        return expressionHandler;
    }
}
