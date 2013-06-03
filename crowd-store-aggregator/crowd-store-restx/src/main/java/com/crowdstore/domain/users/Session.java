package com.crowdstore.domain.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;
import restx.exceptions.ErrorCode;
import restx.exceptions.ErrorField;

public class Session {
    @Id
    final String key;
    final SimpleUser user;

    protected Session(String key, SimpleUser user) {
        this.key = key;
        this.user = user;
    }

    @JsonCreator
    public static Session create(@JsonProperty("key") String key, @JsonProperty("user") SimpleUser user){
        return new Session(key, user);
    }

    public String getKey() {
        return key;
    }

    public SimpleUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Session{");
        sb.append("key='").append(key).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }

    public static class Rules {
        @ErrorCode(code = "SESSION-001", status = 403, description = "invalid user")
        public static enum InvalidUser {
            @ErrorField("login") LOGIN
        }
        @ErrorCode(code = "SESSION-002", status = 403, description = "unconfirmed email")
        public static enum UnconfirmedEmail {
            @ErrorField("login") LOGIN,
            @ErrorField("email") EMAIL
        }
    }
}
