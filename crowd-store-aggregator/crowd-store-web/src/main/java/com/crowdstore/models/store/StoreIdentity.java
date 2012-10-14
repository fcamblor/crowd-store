package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author damienriccio
 */
public class StoreIdentity extends GenericIdentifiable {
    @NotNull
    @Size(min=2,max=40)
    private String name;

    public StoreIdentity(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public StoreIdentity setName(String name) {
        this.name = name;
        return this;
    }
}
