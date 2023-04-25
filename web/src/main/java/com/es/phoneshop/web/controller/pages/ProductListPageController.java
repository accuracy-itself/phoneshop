package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.cart.CartService;
import com.es.core.model.phone.SortOrder;
import com.es.core.model.phone.SortField;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private CartService cartService;

    private final int PHONES_LIMIT_PER_PAGE = 20;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) String query,
                                  @RequestParam(required = false) String sortField,
                                  @RequestParam(required = false) String sortOrder) {

        int offset;
        if(page != null) {
            offset = PHONES_LIMIT_PER_PAGE * (page - 1);
        } else {
            page = 1;
            offset = 0;
        }

        int pagesAmount = (int) Math.ceil((float) phoneDao.countAvailable(query) / PHONES_LIMIT_PER_PAGE);
        model.addAttribute("phones", phoneDao.findAll(query,
                (sortField != null && !sortField.equals("")) ? SortField.valueOf(sortField.toUpperCase()) : null,
                (sortOrder != null && !sortOrder.equals("")) ? SortOrder.valueOf(sortOrder.toUpperCase()) : null,
                offset, PHONES_LIMIT_PER_PAGE));
        model.addAttribute("pagesAmount", pagesAmount);
        model.addAttribute("page", page);
        model.addAttribute("cart", cartService.getCart());
        return "productList";
    }
}
