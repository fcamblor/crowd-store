package com.crowdstore.domain.users;

import com.crowdstore.common.validation.FormModes;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.jongo.marshall.jackson.oid.ObjectId;
import restx.exceptions.ErrorCode;
import restx.exceptions.ErrorField;
import restx.jackson.Views;

import javax.validation.constraints.NotNull;

public class SimpleUser {

    @ObjectId @NotNull(groups={FormModes.Update.class})
    final String key;

    @NotNull
    final String firstName;
    @NotNull
    final String lastName;

    @NotNull @Email
    final String email;

    @NotNull @JsonView(Views.Private.class)
    final String passwordHash;

    protected SimpleUser(String key, String firstName, String lastName, String email, String passwordHash) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    @JsonCreator
    public static SimpleUser createSimpleUser(
            @JsonProperty("key") String key, @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName, @JsonProperty("email") String email,
            @JsonProperty("passwordHash") String passwordHash){

        return new SimpleUser(key, firstName, lastName, email, passwordHash);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleUser{");
        sb.append("key='").append(key).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getKey() {
        return key;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static class Rules {
        @ErrorCode(code = "USER-001", description = "login already exist")
        public static enum LoginAlreadyExist {
            @ErrorField("login") LOGIN
        }
        @ErrorCode(code = "USER-002", description = "email already exist")
        public static enum EmailAlreadyExist {
            @ErrorField("email") EMAIL
        }
    }
}
