package com.crowdstore.web.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

/**
 * @author fcamblor
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    private static Logger LOG = LoggerFactory.getLogger(HelloController.class);

    public static class Result {
        Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public static class Query {
        @NotNull
        Integer leftOperand;
        @NotNull
        Integer rightOperand;

        public Integer getLeftOperand() {
            return leftOperand;
        }

        public void setLeftOperand(Integer leftOperand) {
            this.leftOperand = leftOperand;
        }

        public Integer getRightOperand() {
            return rightOperand;
        }

        public void setRightOperand(Integer rightOperand) {
            this.rightOperand = rightOperand;
        }
    }

    public static enum Operator {
        plus {
            @Override
            public Integer apply(Integer leftOperand, Integer rightOperand) {
                return leftOperand + rightOperand;
            }
        }, minus {
            @Override
            public Integer apply(Integer leftOperand, Integer rightOperand) {
                return leftOperand - rightOperand;
            }
        }, multiply {
            @Override
            public Integer apply(Integer leftOperand, Integer rightOperand) {
                return leftOperand * rightOperand;
            }
        }, divide {
            @Override
            public Integer apply(Integer leftOperand, Integer rightOperand) {
                return Math.round(leftOperand / rightOperand);
            }
        };

        public abstract Integer apply(Integer leftOperand, Integer rightOperand);
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public
    @ResponseBody
        // For @RequestBody to be correctly processed here, you will have to :
        // - Provide JSON bean in request body, something like this : { "leftOperand":1,  "rightOperand":3 }
        // - Set Content-Type header to application/json
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
