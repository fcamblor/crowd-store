package com.crowdstore.rest;

import com.crowdstore.AppServer;
import org.junit.ClassRule;
import org.junit.Test;
import restx.tests.RestxSpecRule;

public class SessionResourceSpecTest {
    @ClassRule
    public static RestxSpecRule rule = new RestxSpecRule(
            AppServer.WEB_INF_LOCATION,
            AppServer.WEB_APP_LOCATION);

    @Test
    public void should_authentication_be_successful() throws Exception {
        rule.runTest("specs/sessions/should_authentication_be_successful.spec.yaml");
    }

    @Test
    public void should_authentication_be_in_failure() throws Exception {
        rule.runTest("specs/sessions/should_authentication_be_in_failure.spec.yaml");
    }

    @Test
    public void should_disconnection_be_successful() throws Exception {
        rule.runTest("specs/sessions/should_disconnection_be_successful.spec.yaml");
    }
}
