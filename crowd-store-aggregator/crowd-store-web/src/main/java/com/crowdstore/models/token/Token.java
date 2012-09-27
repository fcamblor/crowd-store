package com.crowdstore.models.token;

import com.crowdstore.models.common.GenericIdentifiable;

import java.util.Date;

/**
 * @author damienriccio
 */
public class Token extends GenericIdentifiable {
    private String token;
    private Date endValidityDate;

    // todo maybe add a feature that is allowed by this token. For instance : this token allow to create an account ?
}
