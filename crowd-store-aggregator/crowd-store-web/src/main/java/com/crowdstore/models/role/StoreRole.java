package com.crowdstore.models.role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author damienriccio
 */
public enum StoreRole {
    ADMIN {
        @Override
        public List<StoreAuthorization> getAuthorizations() {
            final ArrayList<StoreAuthorization> authorizations = new ArrayList<StoreAuthorization>();
            Collections.addAll(authorizations, StoreAuthorization.values());
            return  authorizations;
        }
    },
    CUSTOMER {
        @Override
        public List<StoreAuthorization> getAuthorizations() {
            return Collections.emptyList();
        }
    };

    public abstract List<StoreAuthorization> getAuthorizations();
}
