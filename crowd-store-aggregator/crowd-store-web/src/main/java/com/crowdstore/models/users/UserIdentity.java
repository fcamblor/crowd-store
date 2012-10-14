package com.crowdstore.models.users;

import com.crowdstore.models.common.GenericIdentifiable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fcamblor
 */
public class UserIdentity extends GenericIdentifiable {
    @NotNull
    @Size(min=2, max=40)
    private String firstName;
    @NotNull
    @Size(min=2, max=40)
    private String lastName;
    @NotNull
    @Email
    @Size(max=40)
    private String email;

    protected UserIdentity() {
        super(null);
    }

    public UserIdentity(Long id) {
        super(id);
    }

    @JsonProperty
    public String getDisplayName() {
        return this.firstName + " " + this.lastName;
    }
    // Weird Jackson behaviour on "calculated" fields : we cannot they the "displayName" field
    // should be serialized and never deserialized in another way than providing a setter for this field..
    @JsonIgnore
    private void setDisplayName(String n) { throw new IllegalStateException("setDisplayName() should never be called !"); }

    public String getFirstName() {
        return this.firstName;
    }

    public UserIdentity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public UserIdentity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public UserIdentity setEmail(String _email) {
        this.email = _email;
        return this;
    }
}
