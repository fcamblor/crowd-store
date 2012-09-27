package com.crowdstore.web.common.interceptors;

import com.crowdstore.models.context.AppContext;
import com.crowdstore.web.common.session.SessionHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fcamblor
 */
public class AppContextInRequestProvider extends HandlerInterceptorAdapter {

    @Inject
    private AppContext appContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.preHandle(request, response, handler);

        // Injecting things in AppContext for every requests here ... will then allow to inject it everywhere
        // Here we are allowed to overwrite current authenticated user... it is a rare case where we are allowed to..
        appContext.setWritableAuthenticatedUser(true);
        appContext.setAuthenticatedUser(SessionHolder.getAuthenticatedUser(request));
        appContext.setWritableAuthenticatedUser(false);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        appContext.setWritableAuthenticatedUser(true);
        appContext.setAuthenticatedUser(null);
        appContext.setWritableAuthenticatedUser(false);

        super.postHandle(request, response, handler, modelAndView);
    }
}
