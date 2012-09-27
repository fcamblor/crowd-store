package com.crowdstore.web.common.interceptors;

import com.crowdstore.common.threads.AppThreadedInfos;
import com.crowdstore.models.context.AppContext;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fcamblor
 *         Spring interceptor allowing to provide App threaded context
 */
public class AppThreadedInfosRequestProvider extends HandlerInterceptorAdapter {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Inject
    @Named("messageSource")
    private MessageSource messageSource;

    @Inject
    private AppContext appContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.preHandle(request, response, handler);

        // Providing injected beans in current thread local
        AppThreadedInfos.provideAppThreadedContextToCurrentThread(
                AppThreadedInfos.Context.create(appContext, messageSource)
        );

        return true;
    }
}
