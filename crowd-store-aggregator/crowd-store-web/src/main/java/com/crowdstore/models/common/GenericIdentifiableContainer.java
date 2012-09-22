package com.crowdstore.models.common;

/**
 * @author fcamblor
 */
public class GenericIdentifiableContainer<T extends Identifiable> implements IdentifiableContainer<T> {
    private T identity;

    public GenericIdentifiableContainer() {
        this(null);
    }

    public GenericIdentifiableContainer(T identity) {
        this.identity = identity;
    }

    @Override
    public T getIdentity() {
        return identity;
    }
}
