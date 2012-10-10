package com.crowdstore.service.tables;

import com.crowdstore.persistence.tables.TableInfosDao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author fcamblor
 */
@Service
public class TableInfosServiceImpl implements TableInfosService {

    @Inject
    TableInfosDao tableInfosDao;

    @Override
    public List<TableInfosDao.TableInfos> getTableInfos() {
        return tableInfosDao.getTableInfos();
    }
}
