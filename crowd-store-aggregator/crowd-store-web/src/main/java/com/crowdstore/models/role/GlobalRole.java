package com.crowdstore.models.role;

import com.crowdstore.models.security.GlobalAuthorization;

import java.util.Arrays;
import java.util.List;

/**
 * @author damienriccio
 */
public enum GlobalRole {
    ADMIN(GlobalAuthorization.values()), SIMPLE_USER();

    List<GlobalAuthorization> authorizations;

    private GlobalRole(GlobalAuthorization... authorizations) {
        this.authorizations = Arrays.asList(authorizations);
    }

    public List<GlobalAuthorization> getAuthorizations() {
        return authorizations;
    }
}
