package com.crowdstore.models.security;

/**
 * @author fcamblor
 */
public enum ConditionnalOperator {
    OR() {
        @Override
        public boolean apply(boolean left, boolean right) {
            return left || right;
        }
    }, AND() {
        @Override
        public boolean apply(boolean left, boolean right) {
            return left && right;
        }
    };

    public abstract boolean apply(boolean left, boolean right);
}
