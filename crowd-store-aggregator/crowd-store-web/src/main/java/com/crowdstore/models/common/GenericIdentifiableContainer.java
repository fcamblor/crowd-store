package com.crowdstore.models.common;

import java.io.Serializable;

/**
 * @author fcamblor
 */
public abstract class GenericIdentifiableContainer<T extends Identifiable> implements IdentifiableContainer<T>, Serializable {
    protected T identity;

    /**
     * This constructor should not be public
     * It is protected just in order to exist for subclasses and
     * introspection-based frameworks such as MyBatis
     */
    protected GenericIdentifiableContainer() {
        this(null);
    }

    public GenericIdentifiableContainer(T identity) {
        this.identity = identity;
    }

    // Note that getIdentity()/setIdentity() are not implemented here for a simple reason : type erasure
    // If we would have implemented these methods here, parameterized type after compilation would have been Identifiable
    // and introspection-based frameworks such as MyBatis will complain in that case, and won't be able to set
    // subclass fields on this types
}
