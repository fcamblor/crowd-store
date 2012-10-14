package com.crowdstore.models.common;

/**
 * @author fcamblor
 */
public class GenericIdentifiable implements Identifiable {
    private Long id;

    /**
     * This constructor should not be public
     * It is protected just in order to exist for subclasses and
     * introspection-based frameworks such as MyBatis
     */
    protected GenericIdentifiable() {
        this(null);
    }

    public GenericIdentifiable(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
