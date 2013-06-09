package com.crowdstore.domain.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.bson.types.ObjectId;
import restx.RestxSession;
import restx.exceptions.ErrorCode;
import restx.exceptions.ErrorField;
import restx.jackson.Views;
import restx.security.RestxPrincipal;

import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @author fcamblor
 */
public class User extends SimpleUser implements RestxPrincipal {

    public static Optional<User> current() {
        return RestxSession.current().get(User.class, RestxPrincipal.SESSION_DEF_KEY);
    }

    @Size(min=1)
    @JsonView(Views.Private.class)
    final Collection<String> profileNames;

    @Size(min=1)
    @JsonView(Views.Transient.class)
    final Collection<String> roles;

    protected User(String key, String firstName, String lastName, String email, String passwordHash, Collection<String> profileNames) {
        super(key, firstName, lastName, email, passwordHash);
        this.profileNames = profileNames;
        this.roles = UserProfile.extractRolesFrom(this.profileNames);
    }

    @Override @JsonIgnore
    public String getName() {
        return getFirstName() + " " +getLastName();
    }

    @Override @JsonIgnore
    public ImmutableSet<String> getPrincipalRoles() {
        return ImmutableSet.copyOf(getRoles());
    }

    @JsonCreator
    public static User createUser(
            @JsonProperty("_id") ObjectId key, @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName, @JsonProperty("email") String email,
            @JsonProperty("passwordHash") String passwordHash, @JsonProperty("profileNames") Collection<String> profileNames){

        return new User(key.toString(), firstName, lastName, email, passwordHash, profileNames);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("profileNames=").append(profileNames);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public static class Rules {
        @ErrorCode(code = "USER-003", status = 403, description = "invalid user")
        public static enum InvalidUser {
            @ErrorField("login") LOGIN
        }
        @ErrorCode(code = "USER-004", status = 403, description = "unconfirmed email")
        public static enum UnconfirmedEmail {
            @ErrorField("login") LOGIN,
            @ErrorField("email") EMAIL
        }
    }
}
