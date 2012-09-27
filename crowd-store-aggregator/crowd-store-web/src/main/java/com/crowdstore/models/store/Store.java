package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiable;

import java.util.List;

/**
 * @author damienriccio
 */
public class Store extends GenericIdentifiable {
    private String name;
    private List<SellingProduct> products;

}
