package com.crowdstore.models.role;

import com.crowdstore.models.security.StoreAuthorization;
import com.google.common.base.Function;

import javax.annotation.Nullable;
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

    public static Function<StoreRole, List<StoreAuthorization>> TO_STORE_AUTHORIZATIONS = new Function<StoreRole, List<StoreAuthorization>>() {
        @Nullable
        @Override
        public List<StoreAuthorization> apply(@Nullable StoreRole input) {
            if(input == null){
                return null;
            }
            return input.getAuthorizations();
        }
    };


    public abstract List<StoreAuthorization> getAuthorizations();
}
