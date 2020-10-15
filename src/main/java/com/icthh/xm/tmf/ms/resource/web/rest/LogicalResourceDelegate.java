package com.icthh.xm.tmf.ms.resource.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.resource.lep.keyresolver.ProfileChannelKeyResolver;
import com.icthh.xm.tmf.ms.resource.web.api.LogicalResourceApiDelegate;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResource;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class LogicalResourceDelegate implements LogicalResourceApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "GetResource", resolver = ProfileChannelKeyResolver.class)
    @PreAuthorize("hasPermission({'id': #id, 'profile': @headerRequestExtractor.profile}, 'RESOURCE.UPDATE')")
    @PrivilegeDescription("Privilege to update a logical resource")
    @Override
    public ResponseEntity<List<LogicalResource>> retrieveLogicalResource(String id) {
        return ResponseEntity.ok().build();
    }
}
