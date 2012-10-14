package com.crowdstore.models.common;

import com.crowdstore.models.common.stereotypes.FormModes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author fcamblor
 */
public class GenericIdentifiable implements Identifiable {
    @NotNull(groups={FormModes.Update.class})
    @Null(groups={FormModes.Creation.class})
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
