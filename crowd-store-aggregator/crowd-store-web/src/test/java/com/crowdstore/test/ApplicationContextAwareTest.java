package com.crowdstore.test;

/**
 * @author fcamblor
 */

import com.crowdstore.test.mockcontext.WebContextLoader;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

/**
 * @author fcamblor
 * Base class for unit tests with spring context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = WebContextLoader.class,
        locations = {"classpath:applicationContext.xml"})
public abstract class ApplicationContextAwareTest {

    @Inject
    private WebApplicationContext wac;

    @Before
    public void setup(){
        // Mocking web application context
        MockMvc mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
    }

}