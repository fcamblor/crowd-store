package com.crowdstore.models.user;

import com.crowdstore.models.common.GenericIdentifiable;

import java.util.List;

/**
 * @author damienriccio
 */
public class User extends GenericIdentifiable {
    private String email;
    private String firstName;
    private String lastName;
    private String encodedPassword;
    private List<UserStoreRole> userStoreRoles;
    private List<User> friends;
    private Boolean isGlobalAdmin;

    public User() {
    }

    public User(Long id) {
        super(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public List<UserStoreRole> getUserStoreRoles() {
        return userStoreRoles;
    }

    public void setUserStoreRoles(List<UserStoreRole> userStoreRoles) {
        this.userStoreRoles = userStoreRoles;
    }

    public Boolean getGlobalAdmin() {
        return isGlobalAdmin;
    }

    public void setGlobalAdmin(Boolean globalAdmin) {
        isGlobalAdmin = globalAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (encodedPassword != null ? !encodedPassword.equals(user.encodedPassword) : user.encodedPassword != null)
            return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (isGlobalAdmin != null ? !isGlobalAdmin.equals(user.isGlobalAdmin) : user.isGlobalAdmin != null)
            return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (userStoreRoles != null ? !userStoreRoles.equals(user.userStoreRoles) : user.userStoreRoles != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (encodedPassword != null ? encodedPassword.hashCode() : 0);
        result = 31 * result + (userStoreRoles != null ? userStoreRoles.hashCode() : 0);
        result = 31 * result + (isGlobalAdmin != null ? isGlobalAdmin.hashCode() : 0);
        return result;
    }
}
