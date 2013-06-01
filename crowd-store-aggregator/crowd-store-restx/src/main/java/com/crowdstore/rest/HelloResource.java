package com.crowdstore.rest;

import com.crowdstore.domain.Message;
import org.joda.time.DateTime;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;

@Component @RestxResource
public class HelloResource {
    @GET("/message")
    public Message sayHello(String who) {
        return new Message().setMessage(String.format(
                "hello %s, it's %s",
                who, DateTime.now().toString("HH:mm:ss")));
    }
}
