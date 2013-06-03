package com.crowdstore.domain.users;

import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fcamblor
 */
public abstract class UserProfile {
    public static final UserProfile.Fixed SIMPLE_USER = new UserProfile.Fixed("SIMPLE_USER",
        UserRole.Fixed.CREATE_STORE,
        UserRole.Fixed.UPDATE_OWN_USERINFOS
    );
    public static final UserProfile.Fixed GLOBAL_ADMIN = new UserProfile.Fixed("GLOBAL_ADMIN",
        UserRole.Fixed.values()
    );
    public static final UserProfile.Dynamic STORE_MAINTAINER = new UserProfile.Dynamic("STORE_MAINTAINER", null,
        UserRole.PerStore.ADD_NEW_PRODUCTS_IN_STORE,
        UserRole.PerStore.ADJUST_PRODUCT_STOCK_IN_STORE,
        UserRole.PerStore.INVITE_USER_IN_STORE
    );

    private static final List<Parser> AVAILABLE_USER_PROFILE_PARSERS = Arrays.asList(
            Fixed.createParser(SIMPLE_USER), Fixed.createParser(GLOBAL_ADMIN),
            Dynamic.createParser(STORE_MAINTAINER)
    );

    private static interface Parser {
        UserProfile parse(String profileName);
    }

    public static class Fixed extends UserProfile {
        private static class Parser implements UserProfile.Parser {
            private Fixed profile;
            private Parser(Fixed profile) {
                this.profile = profile;
            }
            @Override
            public UserProfile parse(String profileName) {
                if(this.profile.getProfileNameRadix().equals(profileName)){
                    return this.profile;
                }
                return null;
            }
        }

        private List<UserRole.Fixed> roles;

        public Fixed(String profileNameRadix, UserRole.Fixed... roles){
            super(profileNameRadix);
            this.roles = Arrays.asList(roles);
        }

        @Override
        protected Collection<String> getRoleNames() {
            return Collections2.transform(this.roles, UserRole.Fixed.EXTRACT_NAME_FCT);
        }

        @Override
        public String getProfileName(){
            return this.getProfileNameRadix();
        }

        public static UserProfile.Parser createParser(Fixed userProfile){
            return new Parser(userProfile);
        }
    }

    public static class Dynamic extends UserProfile {
        private static class Parser implements UserProfile.Parser {
            private static final Pattern EXTRACT_ID_PATTERN = Pattern.compile(".+\\((.+)\\)");
            private Dynamic profile;
            private Parser(Dynamic profile) {
                this.profile = profile;
            }
            @Override
            public UserProfile parse(String profileName) {
                if(profileName.startsWith(this.profile.getProfileNameRadix())){
                    Matcher idMatcher = EXTRACT_ID_PATTERN.matcher(profileName);
                    if(idMatcher.matches()){
                        return this.profile.cloneWithId(idMatcher.group(1));
                    }
                }
                return null;
            }
        }

        private String id;
        private List<UserRole.Dynamic> roles;

        public Dynamic(String profileNameRadix, String id, UserRole.Dynamic... roles){
            super(profileNameRadix);
            this.id = id;
            this.roles = Arrays.asList(roles);
        }

        @Override
        protected Collection<String> getRoleNames() {
            return Collections2.transform(this.roles, UserRole.Dynamic.EXTRACT_NAME_FCT);
        }

        public Dynamic cloneWithId(String id){
            List<UserRole.Dynamic> clonedRoles = new ArrayList<>();
            for(UserRole.Dynamic role : this.roles){
                clonedRoles.add(role.cloneWithId(id));
            }
            return new Dynamic(this.getProfileNameRadix(), id, clonedRoles.toArray(new UserRole.Dynamic[0]));
        }

        @Override
        public String getProfileName(){
            return new StringBuilder(this.getProfileNameRadix())
                    .append("(").append(this.id).append(")").toString();
        }

        public static UserProfile.Parser createParser(Dynamic userProfile){
            return new Parser(userProfile);
        }
    }

    private String profileNameRadix;

    protected UserProfile(String profileNameRadix){
        this.profileNameRadix = profileNameRadix;
    }

    protected String getProfileNameRadix() {
        return profileNameRadix;
    }

    protected abstract Collection<String> getRoleNames();
    public abstract String getProfileName();

    public static Collection<String> extractRolesFrom(Collection<String> profileNames){
        Collection<String> roles = new ArrayList<>();
        for(String profileName : profileNames){
            for(Parser parser : AVAILABLE_USER_PROFILE_PARSERS){
                UserProfile profile = parser.parse(profileName);
                if(profile != null){
                    roles.addAll(profile.getRoleNames());
                }
            }
        }
        return roles;
    }

}
