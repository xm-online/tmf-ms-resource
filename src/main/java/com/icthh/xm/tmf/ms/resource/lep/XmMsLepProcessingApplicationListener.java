package com.icthh.xm.tmf.ms.resource.lep;

import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_KEY_COMMONS;
import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_KEY_JDBC;
import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_KEY_SERVICES;
import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_KEY_TEMPLATES;
import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_PERMISSION_SERVICE;
import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_SERVICE_TENANT_CONFIG_SERICE;
import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_TEMPLATE_REST;
import static com.icthh.xm.tmf.ms.resource.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_SERVICE_SEPARATE_TRANSACTION_EXECUTOR;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.commons.CommonsExecutor;
import com.icthh.xm.commons.lep.commons.CommonsService;
import com.icthh.xm.commons.lep.spring.SpringLepProcessingApplicationListener;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.lep.api.ScopedContext;
import com.icthh.xm.tmf.ms.resource.service.SeparateTransactionExecutor;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * The {@link XmMsLepProcessingApplicationListener} class.
 */
@RequiredArgsConstructor
public class XmMsLepProcessingApplicationListener extends SpringLepProcessingApplicationListener {


    private final TenantConfigService tenantConfigService;

    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;

    private final CommonsService commonsService;
    private final PermissionCheckService permissionCheckService;
    private final SeparateTransactionExecutor separateTransactionExecutor;


    @Override
    protected void bindExecutionContext(ScopedContext executionContext) {
        // services
        Map<String, Object> services = new HashMap<>();

        services.put(BINDING_SUB_KEY_SERVICE_TENANT_CONFIG_SERICE, tenantConfigService);
        services.put(BINDING_SUB_KEY_PERMISSION_SERVICE, permissionCheckService);
        services.put(BINDING_SUB_KEY_SERVICE_SEPARATE_TRANSACTION_EXECUTOR, separateTransactionExecutor);

        executionContext.setValue(BINDING_KEY_COMMONS, new CommonsExecutor(commonsService));
        executionContext.setValue(BINDING_KEY_SERVICES, services);

        // templates
        Map<String, Object> templates = new HashMap<>();
        templates.put(BINDING_SUB_KEY_TEMPLATE_REST, restTemplate);
        templates.put(BINDING_KEY_JDBC, jdbcTemplate);

        executionContext.setValue(BINDING_KEY_TEMPLATES, templates);
    }

}
