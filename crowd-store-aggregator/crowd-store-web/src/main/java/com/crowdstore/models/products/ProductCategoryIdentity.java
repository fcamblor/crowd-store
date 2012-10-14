package com.crowdstore.models.products;

import com.crowdstore.models.common.GenericIdentifiable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fcamblor
 */
public class ProductCategoryIdentity extends GenericIdentifiable {
    @NotNull
    @Size(min=2,max=40)
    private String name;

    public ProductCategoryIdentity(){ super(null); }
    public ProductCategoryIdentity(Long id){ super(id); }

    public ProductCategoryIdentity setName(String _name){
        this.name = _name;
        return this;
    }

    public String getName(){
        return this.name;
    }

}
