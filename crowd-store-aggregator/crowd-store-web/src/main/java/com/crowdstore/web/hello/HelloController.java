package com.crowdstore.web.hello;

import com.crowdstore.service.hello.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * @author fcamblor
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    private static Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Inject
    HelloService helloService;

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
        r.value = Operator.plus.apply(query.leftOperand, query.rightOperand);
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
        r.value = Operator.plus.apply(query.leftOperand, query.rightOperand);
        return r;
    }
}
