package com.icthh.xm.tmf.ms.resource.lep.keyresolver;

import com.icthh.xm.commons.lep.AppendLepKeyResolver;
import com.icthh.xm.lep.api.LepManagerService;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.lep.api.commons.SeparatorSegmentedLepKey;
import com.icthh.xm.tmf.ms.resource.utils.HeaderRequestExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProfileChannelKeyResolver extends AppendLepKeyResolver {

    private final HeaderRequestExtractor headerRequestExtractor;

    @Override
    protected String[] getAppendSegments(SeparatorSegmentedLepKey baseKey,
                                         LepMethod method,
                                         LepManagerService managerService) {

        String profile = translateToLepConvention(headerRequestExtractor.get("Profile"));
        String channelId = translateToLepConvention(headerRequestExtractor.get("Channel"));
        return new String[]{profile, channelId};
    }
}
