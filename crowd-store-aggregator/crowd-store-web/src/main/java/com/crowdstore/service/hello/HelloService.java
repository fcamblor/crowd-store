package com.crowdstore.service.hello;

import com.crowdstore.models.hello.HelloModel;

/**
 * @author fcamblor
 */
public interface HelloService {
    String sayHello(String whom);

    HelloModel calculate(Integer value);
}
