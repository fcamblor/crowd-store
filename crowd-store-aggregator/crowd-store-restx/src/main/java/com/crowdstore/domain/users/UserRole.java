package com.crowdstore.domain.users;

import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * @author fcamblor
 */
public interface UserRole {
    public static enum Fixed implements UserRole {
        CREATE_STORE, UPDATE_OWN_USERINFOS, UPDATE_OTHERS_USERINFOS;

        public static Function<Fixed, String> EXTRACT_NAME_FCT = new Function<Fixed, String>() {
            @Override
            public String apply(@Nullable UserRole.Fixed input) {
                return input.name();
            }
        };

    }
    public static abstract class Dynamic implements UserRole {
        protected String name;
        protected String id;
        protected Dynamic(String name, String id){
            this.name = name;
            this.id = id;
        }

        public abstract Dynamic cloneWithId(String id);
        public static Function<Dynamic, String> EXTRACT_NAME_FCT = new Function<Dynamic, String>() {
            @Override
            public String apply(@Nullable UserRole.Dynamic input) {
                return new StringBuilder(input.name)
                        .append("(").append(input.id).append(")").toString();
            }
        };
    }
    public static class PerStore extends Dynamic {
        public static PerStore INVITE_USER_IN_STORE = new PerStore("INVITE_USER_IN_STORE");
        public static PerStore ADD_NEW_PRODUCTS_IN_STORE = new PerStore("ADD_NEW_PRODUCTS_IN_STORE");
        public static PerStore ADJUST_PRODUCT_STOCK_IN_STORE = new PerStore("ADJUST_PRODUCT_STOCK_IN_STORE");

        private PerStore(String name){ this(name, null); }
        private PerStore(String name, String id){ super(name, id); }
        public PerStore cloneWithId(String id){ return new PerStore(this.name, id); }
    }
}
