package com.crowdstore.models.common;

/**
 * @author fcamblor
 */
public class GenericIdentifiable implements Identifiable {
    private Long id;

    /**
     * This constructor should never be called directly in application code
     * It is only present for introspection frameworks such as MyBatis which need them to instantiate
     * new instances of the Identifiable
     */
    public GenericIdentifiable() {
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
