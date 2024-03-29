package com.icthh.xm.tmf.ms.resource.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.resource.lep.keyresolver.ProfileChannelKeyResolver;
import com.icthh.xm.tmf.ms.resource.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.resource.web.api.LogicalResourceApiDelegate;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResource;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResourceCreate;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResourceUpdate;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class LogicalResourceDelegate implements LogicalResourceApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "GetResource", resolver = ProfileChannelKeyResolver.class)
    @PreAuthorize("hasPermission({'id': #id, 'profile': @headerRequestExtractor.get('profile')}, 'RESOURCE.GET.LIST')")
    @PrivilegeDescription("Privilege to retrieve a logical resource")
    @Override
    public ResponseEntity<List<LogicalResource>> retrieveLogicalResource(String id) {
        return ResponseEntity.ok().build();
    }

    @Timed
    @LogicExtensionPoint(value = "ListResource", resolver = ProfileChannelKeyResolver.class)
    @PreAuthorize("hasPermission({'id': #id, 'profile': @headerRequestExtractor.get('profile')}, 'RESOURCE.LOGICAL.LIST')")
    @PrivilegeDescription("Privilege to list logical resource")
    @Override
    public ResponseEntity<List<LogicalResource>> listLogicalResource(String fields, String atBaseType, String atSchemaLocation, String atType,
                                                                     String category, OffsetDateTime endDate, String lifecycleState, String name,
                                                                     OffsetDateTime startDate, String version, String value, String resourceRelationshipType,
                                                                     String characteristicName, String characteristicValue, String placeRole, String partyRoleName,
                                                                     String partyRolePartyId, String partyRolePartyName, String relatedPartyName,
                                                                     String relatedPartyRole, String noteAuthor, OffsetDateTime noteDate, String noteText, String resourceStatus) {
        return ResponseEntity.ok().build();
    }

    @LogicExtensionPoint(value = "UpdateResource", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'id': #id, 'profile': @headerRequestExtractor.get('profile')}, 'RESOURCE.UPDATE')")
    @PrivilegeDescription("Privilege to update a logical resource")
    @Override
    public ResponseEntity<LogicalResource> updateLogicalResource(String id, LogicalResourceUpdate logicalResource) {
        return ResponseEntity.ok().build();
    }

    @LogicExtensionPoint(value = "PatchResource", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'id': #id, 'profile': @headerRequestExtractor.get('profile')}, 'RESOURCE.UPDATE')")
    @PrivilegeDescription("Privilege to update a logical resource")
    @Override
    public ResponseEntity<LogicalResource> patchLogicalResource(String id, LogicalResourceUpdate logicalResource) {
        return ResponseEntity.ok().build();
    }

    @LogicExtensionPoint(value = "CreateResource", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.get('profile')}, 'RESOURCE.CREATE')")
    @PrivilegeDescription("Privilege to create a logical resource")
    @Override
    public ResponseEntity<LogicalResource> createLogicalResource(LogicalResourceCreate logicalResource) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
