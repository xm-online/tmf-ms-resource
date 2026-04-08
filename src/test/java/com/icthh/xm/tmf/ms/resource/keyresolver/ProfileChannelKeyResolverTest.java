package com.icthh.xm.tmf.ms.resource.keyresolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.icthh.xm.tmf.ms.resource.lep.keyresolver.ProfileChannelKeyResolver;
import com.icthh.xm.tmf.ms.resource.utils.HeaderRequestExtractor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class ProfileChannelKeyResolverTest {
    private static final String PROFILE_KEY = "Profile";
    private static final String PROFILE_VALUE = "TEST-PROFILE";
    private static final String CHANNEL_KEY = "Channel";
    private static final String CHANNEL_VALUE = "TEST-CHANNEL";

    @Test
    void shouldResolveLepByHeader() {
        HeaderRequestExtractor headerRequestExtractor = new HeaderRequestExtractor();
        ProfileChannelKeyResolver resolver = new ProfileChannelKeyResolver(headerRequestExtractor);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(PROFILE_KEY, PROFILE_VALUE);
        request.addHeader(CHANNEL_KEY, CHANNEL_VALUE);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<String> segments = resolver.segments(null);

        assertEquals(List.of(PROFILE_VALUE, CHANNEL_VALUE), segments);
    }
}
