package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiableContainer;

/**
 * @author damienriccio
 */
public class Store extends GenericIdentifiableContainer<StoreIdentity> {
    /**
     * @deprecated Use constructor with identity instead !
     */
    public Store() {
        super(null);
    }

    public Store(StoreIdentity identity) {
        super(identity);
    }

    @Override
    public StoreIdentity getIdentity() {
        return identity;
    }
}
