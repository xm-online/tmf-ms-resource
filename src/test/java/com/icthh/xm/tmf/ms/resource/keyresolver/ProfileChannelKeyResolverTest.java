package com.icthh.xm.tmf.ms.resource.keyresolver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.icthh.xm.commons.lep.XmLepConstants;
import com.icthh.xm.commons.lep.spring.LepServiceHandler;
import com.icthh.xm.lep.api.LepKey;
import com.icthh.xm.lep.api.LepKeyResolver;
import com.icthh.xm.lep.api.LepManager;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.lep.api.Version;
import com.icthh.xm.lep.core.CoreLepManager;
import com.icthh.xm.tmf.ms.resource.lep.keyresolver.ProfileChannelKeyResolver;
import com.icthh.xm.tmf.ms.resource.utils.HeaderRequestExtractor;
import com.icthh.xm.tmf.ms.resource.web.api.model.LogicalResourceUpdate;
import com.icthh.xm.tmf.ms.resource.web.rest.LogicalResourceDelegate;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(SpringExtension.class)
class ProfileChannelKeyResolverTest {
    private static final String PROFILE_KEY = "Profile";
    private static final String PROFILE_VALUE = "TEST-PROFILE";
    private static final String PROFILE_VALUE_RESOLVED = "TEST_PROFILE";
    private static final String CHANNEL_KEY = "Channel";
    private static final String CHANNEL_VALUE = "TEST-CHANNEL";
    private static final String CHANNEL_VALUE_RESOLVED = "TEST_CHANNEL";

    @InjectMocks
    private LepServiceHandler lepServiceHandler;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private CoreLepManager lepManager;

    @Captor
    private ArgumentCaptor<LepKey> baseLepKey;

    @Captor
    private ArgumentCaptor<LepKeyResolver> keyResolver;

    @Captor
    private ArgumentCaptor<LepMethod> lepMethod;

    @Captor
    private ArgumentCaptor<Version> version;

    @Test
    void shouldResolveLepByHeader() throws Throwable {

        Method method = LogicalResourceDelegate.class.getMethod("retrieveLogicalResource", String.class);

        when(applicationContext.getBean(LepManager.class)).thenReturn(lepManager);

        HeaderRequestExtractor headerRequestExtractor = new HeaderRequestExtractor();
        ProfileChannelKeyResolver resolver = new ProfileChannelKeyResolver(headerRequestExtractor);
        when(applicationContext.getBean(ProfileChannelKeyResolver.class)).thenReturn(resolver);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(PROFILE_KEY, PROFILE_VALUE);
        request.addHeader(CHANNEL_KEY, CHANNEL_VALUE);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        lepServiceHandler.onMethodInvoke(LogicalResourceDelegate.class,
            new LogicalResourceDelegate(), method, new Object[]{null});

        verify(lepManager)
            .processLep(baseLepKey.capture(), version.capture(), keyResolver.capture(), lepMethod.capture());

        LepKey resolvedKey = resolver.resolve(baseLepKey.getValue(), lepMethod.getValue(), null);

        assertEquals(
            String.join(XmLepConstants.EXTENSION_KEY_SEPARATOR,
                "service", "GetResource", PROFILE_VALUE_RESOLVED, CHANNEL_VALUE_RESOLVED),
                resolvedKey.getId());
    }
}
