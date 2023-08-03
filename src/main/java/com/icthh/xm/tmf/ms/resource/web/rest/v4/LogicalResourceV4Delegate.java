package com.icthh.xm.tmf.ms.resource.web.rest.v4;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.resource.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.resource.web.api.model.v4.LogicalResource;
import com.icthh.xm.tmf.ms.resource.web.api.model.v4.LogicalResourceUpdate;
import com.icthh.xm.tmf.ms.resource.web.api.v4.LogicalResourceV4ApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class LogicalResourceV4Delegate implements LogicalResourceV4ApiDelegate {

    @LogicExtensionPoint(value = "UpdateResourceV4", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'id': #id, 'profile': @headerRequestExtractor.get('profile')}, 'RESOURCE.UPDATE')")
    @PrivilegeDescription("Privilege to update a logical resource")
    @Override
    public ResponseEntity<LogicalResource> updateLogicalResource(String id, LogicalResourceUpdate logicalResource) {
        return ResponseEntity.ok().build();
    }
}
