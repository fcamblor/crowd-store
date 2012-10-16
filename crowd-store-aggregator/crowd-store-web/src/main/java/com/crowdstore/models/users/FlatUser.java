package com.crowdstore.models.users;

import com.crowdstore.models.common.GenericIdentifiableContainer;
import com.crowdstore.models.role.GlobalRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fcamblor
 */
public class FlatUser extends GenericIdentifiableContainer<UserIdentity> {

    @NotNull
    @Size(min=128,max=128)
    private String hashedPassword;
    @NotNull
    private GlobalRole globalRole;
    @NotNull
    @Size(min=128,max=128)
    private String privateToken;

    protected FlatUser(){ super(); }
    public FlatUser(UserIdentity identity){ super(identity); }

    @Override
    public UserIdentity getIdentity() {
        return identity;
    }

    @Override
    public void setIdentity(UserIdentity identity) {
        this.identity = identity;
    }

    public FlatUser setHashedPassword(String _hashedPassword){
        this.hashedPassword = _hashedPassword;
        return this;
    }
    
    public String getHashedPassword(){
        return this.hashedPassword;
    }

    public FlatUser setGlobalRole(GlobalRole _globalRole){
        this.globalRole = _globalRole;
        return this;
    }

    public GlobalRole getGlobalRole(){
        return this.globalRole;
    }

    public String getGlobalRoleName(){
        return getGlobalRole().name();
    }

    public FlatUser setPrivateToken(String _privateToken){
        this.privateToken = _privateToken;
        return this;
    }

    public String getPrivateToken(){
        return this.privateToken;
    }

}
