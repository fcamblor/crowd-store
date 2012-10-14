package com.crowdstore.models.users;

import com.crowdstore.models.common.GenericIdentifiableContainer;

/**
 * @author fcamblor
 */
public class User extends GenericIdentifiableContainer<UserIdentity> {

    protected User() { super(null); }
    public User(UserIdentity identity) {
        super(identity);
    }

    @Override
    public UserIdentity getIdentity() {
        return identity;
    }

    @Override
    public void setIdentity(UserIdentity identity) {
        this.identity = identity;
    }
}
