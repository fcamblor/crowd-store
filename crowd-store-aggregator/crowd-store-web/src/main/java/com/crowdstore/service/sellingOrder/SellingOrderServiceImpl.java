package com.crowdstore.service.sellingOrder;

import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.sellingOrder.FlatSellingOrder;
import com.crowdstore.models.sellingOrder.SellingOrderStatus;
import com.crowdstore.persistence.sellingOrder.SellingOrderDao;
import com.crowdstore.service.security.PermissionsService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author damienriccio
 * @date 11/17/12
 */
@Service
public class SellingOrderServiceImpl implements SellingOrderService {
    @Inject
    SellingOrderDao sellingOrderDao;

    @Inject
    PermissionsService permissionsService;

    @Inject
    AppContext appContext;

    public void create(FlatSellingOrder flatSellingOrder) {
        flatSellingOrder.getIdentity().setOrderDate(new DateTime().toDate());

        if (flatSellingOrder.getUserId().equals(appContext.getAuthenticatedUser().getIdentity().getId())) {
            flatSellingOrder.getIdentity().setStatus(SellingOrderStatus.VALIDATED);
        } else {
            // todo do other things : notify user, check if connected user can create selling order for an other one
            flatSellingOrder.getIdentity().setStatus(SellingOrderStatus.WAITING_FOR_VALIDATION);
        }

        sellingOrderDao.create(flatSellingOrder);
    }
}
