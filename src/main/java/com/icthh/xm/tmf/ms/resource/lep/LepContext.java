package com.icthh.xm.tmf.ms.resource.lep;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.api.BaseLepContext;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.tmf.ms.resource.service.SeparateTransactionExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

public class LepContext extends BaseLepContext {

    public Object commons;
    public Services services;
    public Templates templates;

    public static class Services {
        public TenantConfigService tenantConfigService;
        public PermissionCheckService permissionService;
        public SeparateTransactionExecutor separateTransactionExecutor;
    }

    public static class Templates {
        public RestTemplate rest;
        public JdbcTemplate jdbc;
    }
}
