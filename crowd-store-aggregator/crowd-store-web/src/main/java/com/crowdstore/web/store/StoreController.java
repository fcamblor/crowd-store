package com.crowdstore.web.store;

import com.crowdstore.models.store.Store;
import com.crowdstore.service.store.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

/**
 * @author damienriccio
 */
@Controller
@RequestMapping("/store")
public class StoreController {
    private static Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Inject
    StoreService storeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Store> sayHello() {
        return storeService.getAllStores();
    }
}
