package com.crowdstore.models.common.stereotypes;

/**
 * @author fcamblor
 * Stereotypes for use in bean validation's groups
 * It must be classes since enum type can't be passed to annotation parameters (like bean validation ones)
 */
public interface FormModes {
    public static interface Creation {
    }
    public static interface Update {
    }
    public static interface CreateOrUpdate {
    }
}
