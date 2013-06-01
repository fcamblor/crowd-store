package com.crowdstore.rest;

import com.crowdstore.AppServer;
import org.junit.ClassRule;
import org.junit.Test;
import restx.tests.RestxSpecRule;

public class HelloResourceSpecTest {
    @ClassRule
    public static RestxSpecRule rule = new RestxSpecRule(
            AppServer.WEB_INF_LOCATION,
            AppServer.WEB_APP_LOCATION);

    @Test
    public void should_say_hello() throws Exception {
        rule.runTest("specs/hello/should_say_hello.spec.yaml");
    }
}
