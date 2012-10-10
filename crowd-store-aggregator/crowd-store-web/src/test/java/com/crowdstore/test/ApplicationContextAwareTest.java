package com.crowdstore.test;

/**
 * @author fcamblor
 */

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author fcamblor
 * Base class for unit tests with spring context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath:applicationContext.xml"})
public abstract class ApplicationContextAwareTest {


}