package com.crowdstore.service.hello;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.hello.HelloModel;
import com.crowdstore.persistence.hello.HelloDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author fcamblor
 */
@Service
public class HelloServiceImpl implements HelloService {
    @InjectLogger
    private Logger LOGGER;

    @Inject
    HelloDao helloDao;

    @Inject
    AppContext appContext;

    @Override
    public String sayHello(String whom) {
        LOGGER.info("Hello {}", whom);
        return String.format("Hello %s", whom);
    }

    @Override
    public HelloModel calculate(Integer value) {
        String currentUserDisplayName = appContext.getAuthenticatedUser() == null ? "" : appContext.getAuthenticatedUser().getIdentity().getDisplayName();
        return helloDao.findModel(value + currentUserDisplayName.length());
    }
}
