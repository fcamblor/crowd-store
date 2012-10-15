package com.crowdstore.test.rules;

import com.crowdstore.web.util.TomcatEmbed;
import com.jayway.restassured.RestAssured;
import org.apache.catalina.LifecycleException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;

/**
 * @author fcamblor
 * JUnit rule which will ensure tomcat embed server is started during the test
 * It will configure rest assured to connect to started tomcat port, too.
 */
public class RequiresRunningEmbeddedTomcat extends TestWatcher {

    static protected TomcatEmbed tomcat = null;

    @Override
    protected void starting(Description description) {
        super.starting(description);

        // Starting tomcat embed only once per JVM
        if(tomcat == null){
            try {
                tomcat = new TomcatEmbed().start();
            } catch (LifecycleException | IOException e) {
                throw new RuntimeException(e);
            }
            RestAssured.port = tomcat.getPort();
        }
    }

    @Override
    protected void finished(Description description) {
        /* We don't want to stop tomcat after test execution :
           Tomcat will shutdown when the JVM stops !
        if(tomcat != null){
            try {
                tomcat.stop();
            } catch (LifecycleException e) {
                throw new RuntimeException(e);
            }
        }
        */
        super.finished(description);
    }

    public TomcatEmbed getTomcatEmbed(){
        return tomcat;
    }
}
