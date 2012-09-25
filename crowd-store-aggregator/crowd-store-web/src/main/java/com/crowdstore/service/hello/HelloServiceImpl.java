package com.crowdstore.service.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author fcamblor
 */
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(String whom) {
        LOGGER.info("Hello {}", whom);
        return String.format("Hello %s", whom);
    }
}
