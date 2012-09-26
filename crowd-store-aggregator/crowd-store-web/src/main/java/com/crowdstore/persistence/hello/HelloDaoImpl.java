package com.crowdstore.persistence.hello;

import com.crowdstore.models.hello.HelloModel;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author fcamblor
 */
@Repository
public class HelloDaoImpl extends SqlSessionDaoSupport implements HelloDao {

    @Override
    public HelloModel findModel(Integer value) {
        return DaoSupport.selectOne(this, "selectHello", value);
    }
}
