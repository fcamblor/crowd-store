package com.crowdstore.persistence.tables;

import java.util.List;

/**
 * @author fcamblor
 */
public interface TableInfosDao {
    public static class TableInfos {
        String tableName;
        Integer rowCount;
        public TableInfos setRowCount(Integer _rowCount){
            this.rowCount = _rowCount;
            return this;
        }
        public Integer getRowCount(){
            return this.rowCount;
        }
        public TableInfos setTableName(String _tableName){
            this.tableName = _tableName;
            return this;
        }
        public String getTableName(){
            return this.tableName;
        }
    }

    List<TableInfos> getTableInfos();
}
