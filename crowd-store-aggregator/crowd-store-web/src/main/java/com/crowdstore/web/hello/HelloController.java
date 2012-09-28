package com.crowdstore.web.hello;

import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.service.hello.HelloService;
import com.crowdstore.web.common.session.SessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author fcamblor
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    private static Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Inject
    HelloService helloService;

    @Inject
    AppContext appContext;

    @RequestMapping(value = "/sayHello/{whom}", method = RequestMethod.GET)
    // @PathVariable stands for url path parameters
    public ModelAndView sayHello(@PathVariable String whom) {
        String sentence = helloService.sayHello(whom);

        // When returning a ModelAndView, spring will forward current request to
        // a jsp in WEB-INF/jsp/<view name>.jsp (see app-servlet.xml)
        ModelAndView mav = new ModelAndView("hello/sayHello");
        mav.addObject("sentence", sentence);
        return mav;
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public
    @ResponseBody
        // For @RequestBody to be correctly processed here, you will have to :
        // - Provide JSON bean in request body, something like this : { "leftOperand":1,  "rightOperand":3 }
        // - Set Content-Type header to application/json
        // @Validated will call bean validation, ensuring object is valid when we are in method implementation
    Result calculatePost(@RequestBody @Validated Query query) {
        LOG.info("In calculate() ...");
        Result r = new Result();
        r.value = helloService.calculate(Operator.plus.apply(query.leftOperand, query.rightOperand)).getValue();
        return r;
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    public
    @ResponseBody
        // For a "simple" Object mapping based on query parameters, you will have to call current url
        // with ?leftOperand=1&rightOperand=3, which will be filling the "query" object automatically
    Result calculateGet(@Validated Query query) {
        LOG.info("In calculate() ...");
        Result r = new Result();
        r.value = helloService.calculate(Operator.plus.apply(query.leftOperand, query.rightOperand)).getValue();
        return r;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    // Exceptionnaly, we're passing HttpServletRequest & Response to a spring method, in order to be able to store
    // authenticated user in the session
    public String authenticate(HttpServletRequest request, HttpServletResponse response) {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                new UserIdentity(1l).setEmail("foo@bar.com").setDisplayName("Foo bar " + new SimpleDateFormat("HHmmss").format(new Date()))
        ).setLocale(Locale.FRANCE);

        // Storing authenticatedUser into the session
        SessionHolder.setAuthenticatedUser(request, response, authenticatedUser);

        // Similar to new ModelAndView("hello/authenticationOk")
        return "hello/authenticationOk";
    }

    @RequestMapping(value = "authenticatedUser", method = RequestMethod.GET)
    public
    @ResponseBody
    AuthenticatedUser authenticatedUser() {
        return appContext.getAuthenticatedUser();
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SessionHolder.setAuthenticatedUser(request, response, null);
        return "hello/logoutOk";
    }
}
