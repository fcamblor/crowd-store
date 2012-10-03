package com.crowdstore.models.role;

import com.crowdstore.models.security.GlobalAuthorization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author damienriccio
 */
public enum GlobalRole {
    ADMIN {
        @Override
        public List<GlobalAuthorization> getAuthorizations() {
            final ArrayList<GlobalAuthorization> authorizations = new ArrayList<GlobalAuthorization>();
            Collections.addAll(authorizations, GlobalAuthorization.values());
            return authorizations;
        }
    };

    public abstract List<GlobalAuthorization> getAuthorizations();
}
