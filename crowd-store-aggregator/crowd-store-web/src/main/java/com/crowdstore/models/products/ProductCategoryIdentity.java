package com.crowdstore.models.products;

import com.crowdstore.models.common.GenericIdentifiable;

/**
 * @author fcamblor
 */
public class ProductCategoryIdentity extends GenericIdentifiable {
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
