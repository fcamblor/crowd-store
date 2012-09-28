package com.crowdstore.models.common;

import java.io.Serializable;

/**
 * @author fcamblor
 */
public abstract class GenericIdentifiableContainer<T extends Identifiable> implements IdentifiableContainer<T>, Serializable {
    protected T identity;

    /**
     * Note : subclasses having a constructor without parameters should ideally declare it deprecated
     * in order to not being called by production code.
     * It should be there only for introspection-based frameworks such as MyBatis
     */
    public GenericIdentifiableContainer() {
        this(null);
    }

    public GenericIdentifiableContainer(T identity) {
        this.identity = identity;
    }

    // Note that getIdentity() is not implemented here for a simple reason : type erasure
    // If we would have implemented getIdentity() here, return type after compilation would have been Identifiable
    // and introspection-based frameworks such as MyBatis will complain in that case, and won't be able to set
    // subclass fields on this types
}
