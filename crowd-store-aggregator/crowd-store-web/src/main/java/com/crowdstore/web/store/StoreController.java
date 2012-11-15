package com.crowdstore.web.store;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.products.AvailableProduct;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.service.store.StoreService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

/**
 * @author damienriccio
 */
@Controller
@RequestMapping("/stores")
public class StoreController {
    @InjectLogger
    private Logger LOGGER;

    @Inject
    StoreService storeService;

    @RequestMapping(value="/{storeToken}/users", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserIdentity> getStoreUsers(@PathVariable("storeToken") String storeToken) {
        return storeService.getStoreUsers(storeToken);
    }

    @RequestMapping(value="/{storeToken}/availableProducts", method = RequestMethod.GET)
    public
    @ResponseBody
    List<AvailableProduct> getStoreAvailableProducts(@PathVariable("storeToken") String storeToken) {
        return storeService.getStoreAvailableProducts(storeToken);
    }
}
