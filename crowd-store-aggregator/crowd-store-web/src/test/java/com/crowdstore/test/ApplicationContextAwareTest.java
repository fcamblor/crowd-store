package com.crowdstore.test;

/**
 * @author fcamblor
 */

import com.crowdstore.test.mockcontext.WebContextLoader;
import com.crowdstore.test.rules.LoggingDbChanges;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

/**
 * @author fcamblor
 * Base class for unit tests with spring context.
 */
@ContextConfiguration(
        loader = WebContextLoader.class,
        locations = {"classpath:applicationContext.xml"})
public abstract class ApplicationContextAwareTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private WebApplicationContext wac;

    @Inject
    @Rule
    public LoggingDbChanges loggingDbChanges;

    @Before
    public void setup(){
        // Mocking web application context
        MockMvc mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
    }

}