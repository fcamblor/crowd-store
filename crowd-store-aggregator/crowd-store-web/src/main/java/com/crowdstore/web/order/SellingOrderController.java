package com.crowdstore.web.order;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.common.stereotypes.FormModes;
import com.crowdstore.models.sellingOrder.FlatSellingOrder;
import com.crowdstore.service.sellingOrder.SellingOrderServiceImpl;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * @author damienriccio
 */
@Controller
@RequestMapping("/sellingOrders")
public class SellingOrderController {
    @InjectLogger
    private Logger LOGGER;

    @Inject
    SellingOrderServiceImpl sellingOrderService;

    @RequestMapping(value="/", method= RequestMethod.POST)
    public @ResponseBody void create(@Validated({FormModes.Creation.class}) @RequestBody FlatSellingOrder flatSellingOrder) {
        sellingOrderService.create(flatSellingOrder);
    }
}
