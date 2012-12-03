package com.crowdstore.web.auth;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.service.user.AuthenticationError;
import com.crowdstore.service.user.UserService;
import com.crowdstore.web.common.annotations.PublicSpace;
import com.crowdstore.web.common.session.SessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fcamblor
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    @Inject
    UserService userService;

    @Inject
    AppContext appContext;

    public static class AuthenticatedUserResourceNotAvailable extends Exception {
    }

    @PublicSpace
    @RequestMapping(value = "authenticatedUser", method = RequestMethod.GET)
    public
    @ResponseBody
    AuthenticatedUser authenticatedUser() throws AuthenticatedUserResourceNotAvailable {
        AuthenticatedUser authenticatedUser = appContext.getAuthenticatedUser();
        if (authenticatedUser == null) {
            throw new AuthenticatedUserResourceNotAvailable();
        }
        return authenticatedUser;
    }

    @PublicSpace
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public
    @ResponseBody
    AuthenticatedUser authenticate(HttpServletRequest request, HttpServletResponse response,
                                   @Validated @RequestBody Credentials credentials) throws AuthenticationError {
        AuthenticatedUser authenticatedUser = userService.authenticate(credentials);
        SessionHolder.setAuthenticatedUser(request, response, authenticatedUser);
        return authenticatedUser;
    }

    @ExceptionHandler(AuthenticatedUserResourceNotAvailable.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleAuthenticatedUserNotFound(AuthenticatedUserResourceNotAvailable e) {
        LOG.debug("Authenticated user not found !");
    }

    @ExceptionHandler(AuthenticationError.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void handleAuthenticationError(AuthenticationError authenticationError, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOG.debug("Authentication error !");
    }
}
