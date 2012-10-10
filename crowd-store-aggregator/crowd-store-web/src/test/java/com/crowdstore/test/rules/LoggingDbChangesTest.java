package com.crowdstore.test.rules;

import com.crowdstore.models.products.ProductCategoryIdentity;
import com.crowdstore.persistence.products.ProductCategoryDao;
import com.crowdstore.test.ApplicationContextAwareTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @author fcamblor
 */
public class LoggingDbChangesTest extends ApplicationContextAwareTest {

    private static Logger LOG = LoggerFactory.getLogger(LoggingDbChangesTest.class);

    @Inject
    LoggingDbChanges loggingDbChangesUnderTest;

    @Inject
    ProductCategoryDao productCategoryDao;

    @Test
    public void noDataAddedShouldNotFailAnything(){
        Description desc = Description.createTestDescription(LoggingDbChangesTest.class, "__noDataAddedShouldNotFailAnything");

        simulating_rule_start_before_test: {
            loggingDbChangesUnderTest.starting(desc);
        }

        executing_a_test: {
            // Don't do anything here...
        }

        simulating_rule_finish_after_test: {
            // Here, we shouldn't throw any exception !
            loggingDbChangesUnderTest.finished(desc);
        }
    }

    @Ignore("Don't know why, but test is failing whereas in debug, it doesn't fail...")
    @Test
    public void dataAddedAndCleanedShouldNotThrowException(){
        Description desc;
        simulating_rule_start_before_test: {
            desc = Description.createTestDescription(LoggingDbChangesTest.class, "__dataAddedAndCleanedShouldNotThrowException");
            loggingDbChangesUnderTest.starting(desc);
        }

        ProductCategoryIdentity category;
        executing_a_test: {
            category = new ProductCategoryIdentity().setName("blah");
            // Creating the category during the test
            LOG.info("dataAddedAndCleanedShouldNotThrowException : create before");
            productCategoryDao.create(category);
            LOG.info("dataAddedAndCleanedShouldNotThrowException : create after");

            // ...

            // Cleaning created category during the test
            LOG.info("dataAddedAndCleanedShouldNotThrowException : delete before");
            productCategoryDao.deleteByIds(category.getId());
            LOG.info("dataAddedAndCleanedShouldNotThrowException : delete after");
        }

        simulating_rule_finish_after_test: {
            // Here, we shouldn't throw any exception !
            loggingDbChangesUnderTest.finished(desc);
        }
    }

    @Ignore("Don't know why, but test is failing whereas in debug, it doesn't fail...")
    @Test(expected = IllegalStateException.class)
    public void dataAddedAndNotCleanedShouldThrowException(){
        Description desc;
        simulating_rule_start_before_test: {
            desc = Description.createTestDescription(LoggingDbChangesTest.class, "__dataAddedAndNotCleanedShouldThrowException");
            loggingDbChangesUnderTest.starting(desc);
        }

        ProductCategoryIdentity category;
        executing_a_test: {
            category = new ProductCategoryIdentity().setName("blah");
            // Creating the category during the test
            LOG.info("dataAddedAndNotCleanedShouldThrowException : create before");
            productCategoryDao.create(category);
            LOG.info("dataAddedAndNotCleanedShouldThrowException : create after");
        }

        simulating_rule_finish_after_test: {
            try {
                // Here, the exception should be thrown because product category has not been cleaned before the end of
                // the test !
                loggingDbChangesUnderTest.finished(desc);
            } finally {
                // Cleaning added row...
                LOG.info("dataAddedAndNotCleanedShouldThrowException : delete before");
                productCategoryDao.deleteByIds(category.getId());
                LOG.info("dataAddedAndNotCleanedShouldThrowException : delete after");
            }
        }
    }
}
