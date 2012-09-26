package com.crowdstore.persistence.common;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * @author fcamblor
 *         Helper class allowing to reduce boilerplate code in DAOs based on conventions on mybatis namespaces
 */
public class DaoSupport {

    protected static String createStatement(String queryId, Class<? extends SqlSessionDaoSupport> daoType) {
        return daoType.getCanonicalName() + "." + queryId;
    }

    public static <RETURNTYPE> List<RETURNTYPE> selectList(SqlSessionDaoSupport dao, String queryId) {
        return dao.getSqlSession().selectList(createStatement(queryId, dao.getClass()));
    }

    public static <RETURNTYPE> List<RETURNTYPE> selectList(SqlSessionDaoSupport dao, String queryId, Object parameter) {
        return dao.getSqlSession().selectList(createStatement(queryId, dao.getClass()), parameter);
    }

    public static <RETURNTYPE> RETURNTYPE selectOne(SqlSessionDaoSupport dao, String queryId, Object parameter) {
        return (RETURNTYPE) dao.getSqlSession().selectOne(createStatement(queryId, dao.getClass()), parameter);
    }

    public static void update(SqlSessionDaoSupport dao, String queryId, Object parameter) {
        dao.getSqlSession().update(createStatement(queryId, dao.getClass()), parameter);
    }

    public static void insert(SqlSessionDaoSupport dao, String queryId, Object parameter) {
        dao.getSqlSession().insert(createStatement(queryId, dao.getClass()), parameter);
    }

    public static int delete(SqlSessionDaoSupport dao, String queryId, Object parameter) {
        return dao.getSqlSession().delete(createStatement(queryId, dao.getClass()), parameter);
    }

    public static int deleteByIds(SqlSessionDaoSupport dao, String queryId, Long[] ids) {
        return dao.getSqlSession().delete(createStatement(queryId, dao.getClass()), ids);
    }
}
