package com.icthh.xm.tmf.ms.resource.web.rest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.icthh.xm.commons.i18n.error.web.ExceptionTranslator;
import com.icthh.xm.commons.lep.api.LepManagementService;
import com.icthh.xm.tmf.ms.resource.AbstractSpringBootTest;
import com.icthh.xm.tmf.ms.resource.web.errors.LegacyErrorResponseAdvice;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.WebApplicationContext;

/**
 * Error responses that must stay as they were before the migration (compared with master).
 */
public class LegacyErrorResponseIntTest extends AbstractSpringBootTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LepManagementService lepManagementService;

    @Autowired
    private LegacyErrorResponseAdvice legacyErrorResponseAdvice;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // the LEP interceptor waits until the LEP engines are initialized from the tenant configuration
        lepManagementService.refreshEngines(new HashMap<>());
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void unmappedPathIsNotFound() throws Exception {
        mockMvc.perform(get("/tmf-api/nothing").header("x-tenant", "XM"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void anonymousV4RequestReachesMethodSecurity() throws Exception {
        // on master requests matched by no URL rule were permitted; @PreAuthorize still denies anonymous callers
        mockMvc.perform(put("/tmf-api/resourceInventoryManagement/v4/logicalResource/1").header("x-tenant", "XM")
                .header("profile", "B2C").contentType("application/json;charset=utf-8").content("{\"name\":\"x\"}"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("error.accessDenied"));
    }

    @Test
    public void upstreamServerErrorKeepsItsStatus() throws Exception {
        // a LEP calling lepContext.templates.rest gets a 5xx: answered with the upstream status, as before
        MockMvc upstreamMockMvc = MockMvcBuilders.standaloneSetup(new UpstreamErrorController())
            .setControllerAdvice(legacyErrorResponseAdvice, exceptionTranslator)
            .build();
        upstreamMockMvc.perform(get("/test/legacy-errors/upstream"))
            .andExpect(status().is(HttpStatus.BAD_GATEWAY.value()))
            .andExpect(jsonPath("$.error").value("error.502"));
    }

    @RestController
    static class UpstreamErrorController {

        @GetMapping("/test/legacy-errors/upstream")
        public void upstream() {
            throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "bad gateway");
        }
    }
}
