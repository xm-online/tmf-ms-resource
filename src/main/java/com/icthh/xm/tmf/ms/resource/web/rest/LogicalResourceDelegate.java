package com.icthh.xm.tmf.ms.resource.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.resource.lep.keyresolver.ProfileChannelKeyResolver;
import com.icthh.xm.tmf.ms.resource.web.api.LogicalResourceApiDelegate;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResource;
import java.time.OffsetDateTime;
import java.util.List;
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
                                                                     String relatedPartyRole, String noteAuthor, OffsetDateTime noteDate, String noteText) {
        return ResponseEntity.ok().build();
    }
}
