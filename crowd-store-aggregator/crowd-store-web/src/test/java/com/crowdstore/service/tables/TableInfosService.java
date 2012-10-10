package com.crowdstore.service.tables;

import com.crowdstore.persistence.tables.TableInfosDao;

import java.util.List;

/**
 * @author fcamblor
 */
public interface TableInfosService {
    List<TableInfosDao.TableInfos> getTableInfos();
}
