package com.crowdstore.service.tables;

import com.crowdstore.persistence.tables.TableInfosDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author fcamblor
 */
@Service
public class TableInfosServiceImpl implements TableInfosService {

    private static final Logger LOG = LoggerFactory.getLogger(TableInfosServiceImpl.class);

    @Inject
    TableInfosDao tableInfosDao;

    @Override
    @Transactional
    public List<TableInfosDao.TableInfos> getTableInfos() {
        return tableInfosDao.getTableInfos();
    }
}
