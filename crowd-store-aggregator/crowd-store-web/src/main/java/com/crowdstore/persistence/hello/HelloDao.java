package com.crowdstore.persistence.hello;

import com.crowdstore.models.hello.HelloModel;

/**
 * @author fcamblor
 */
public interface HelloDao {
    HelloModel findModel(Integer value);
}
