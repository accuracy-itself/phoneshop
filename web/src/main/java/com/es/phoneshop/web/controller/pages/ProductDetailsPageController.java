package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.es.core.cart.CartService;
import com.es.core.model.phone.PhoneDao;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private CartService cartService;

    @GetMapping
    public String showProductList(Model model,
                                  @RequestParam(required = true) Long id) {

        model.addAttribute("cart", cartService.getCart());
        phoneDao.get(id).ifPresent(phone -> {
            model.addAttribute("phone", phone);
        });

        return "productDetails";
    }
}
