package com.crowdstore.common.validation;

import javax.validation.groups.Default;

/**
 * @author fcamblor
 * Utility bean validation group classes providing semantics for validation modes
 */
public interface FormModes {
    public static interface Create extends Default {}
    public static interface Update extends Default {}
}
