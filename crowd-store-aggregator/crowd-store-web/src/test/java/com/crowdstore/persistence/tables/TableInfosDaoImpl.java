package com.crowdstore.persistence.tables;

import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fcamblor
 */
@Repository
public class TableInfosDaoImpl extends SqlSessionDaoSupport implements TableInfosDao {
    
    @Value("${db.schemaName}")
    String tableSchema;
    
    @Override
    public List<TableInfos> getTableInfos() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tableSchema", tableSchema);
        return DaoSupport.selectList(this, "getTableInfos", params);
    }
}
