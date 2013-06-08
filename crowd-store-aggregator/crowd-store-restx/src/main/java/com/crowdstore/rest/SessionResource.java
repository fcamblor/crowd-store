package com.crowdstore.rest;

import com.crowdstore.domain.users.Credentials;
import com.crowdstore.domain.users.User;
import com.crowdstore.persistence.UserPersistence;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.joda.time.Duration;
import restx.RestxSession;
import restx.Status;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.exceptions.RestxError;
import restx.factory.Component;
import restx.security.RestxPrincipal;

@Component
@RestxResource
public class SessionResource {
    private final UserPersistence userPersistence;

    public SessionResource(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @GET("/sessions/current")
    public Optional<User> currentUser(){
        return RestxSession.current().get(User.class, RestxPrincipal.SESSION_DEF_KEY);
    }

    @DELETE("/sessions/current")
    public ImmutableMap logout() {
        RestxSession.current().define(User.class, RestxPrincipal.SESSION_DEF_KEY, null);
        return Status.DELETED;
    }

    @POST("/sessions")
    public User authenticate(Credentials credentials) {
        RestxSession.current().define(User.class, RestxPrincipal.SESSION_DEF_KEY, null);
        Optional<User> u = userPersistence.findUserByLoginAndPasswordHash(credentials.getEmail(), credentials.getPasswordHash());
        if (!u.isPresent()) {
            throw RestxError.on(User.Rules.InvalidUser.class)
                        .set(User.Rules.InvalidUser.LOGIN, credentials.getEmail()).raise();
        }

        RestxSession.current().define(User.class, RestxPrincipal.SESSION_DEF_KEY, u.get().getKey());
        RestxSession.current().expires(credentials.isRememberMe() ? Duration.standardDays(30) : Duration.ZERO);

        return u.get();
    }
}
