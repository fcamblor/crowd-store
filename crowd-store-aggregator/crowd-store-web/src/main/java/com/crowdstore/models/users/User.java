package com.crowdstore.models.users;

import com.crowdstore.models.common.GenericIdentifiableContainer;

/**
 * @author fcamblor
 */
public class User extends GenericIdentifiableContainer<UserIdentity> {

    /**
     * @deprecated Use constructor with identity instead !
     */
    public User() {
        super(null);
    }

    public User(UserIdentity identity) {
        super(identity);
    }

    @Override
    public UserIdentity getIdentity() {
        return identity;
    }
}
