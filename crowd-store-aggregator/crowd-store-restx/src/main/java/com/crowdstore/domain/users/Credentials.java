package com.crowdstore.domain.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * @author fcamblor
 */
public class Credentials {

    @NotNull
    final String email;
    @NotNull
    final String passwordHash;
    final boolean rememberMe;

    public Credentials(String email, String passwordHash, boolean rememberMe) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.rememberMe = rememberMe;
    }

    @JsonCreator
    public static Credentials create(@JsonProperty("email") String email, @JsonProperty("passwordHash") String passwordHash,
                                     @JsonProperty("rememberMe") boolean rememberMe) {
        return new Credentials(email, passwordHash, rememberMe);
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Credentials{");
        sb.append("email='").append(email).append('\'');
        sb.append(", passwordHash='").append(passwordHash).append('\'');
        sb.append(", rememberMe=").append(rememberMe);
        sb.append('}');
        return sb.toString();
    }
}
