package com.crowdstore.test;

/**
 * @author fcamblor
 */

import com.crowdstore.test.mockcontext.WebContextLoader;
import com.crowdstore.test.rules.LoggingDbChanges;
import com.crowdstore.test.rules.ModelsTestFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author fcamblor
 * Base class for unit tests with spring context.
 */
@ContextConfiguration(
        loader = WebContextLoader.class,
        locations = {"classpath:applicationContext.xml"})
public abstract class ApplicationContextAwareTest extends AbstractJUnit4SpringContextTests {

    @Inject
    private WebApplicationContext wac;

    @Inject
    protected ModelsTestFactory modelsTestFactory;

    @Inject
    protected LoggingDbChanges loggingDbChanges;

    @Rule
    public TestRule rulesChain;

    @PostConstruct
    protected void defineRuleChain(){
        rulesChain = RuleChain.outerRule(loggingDbChanges).around(modelsTestFactory);
    }

    @Before
    public void setup(){
        // Mocking web application context
        MockMvc mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
    }

}