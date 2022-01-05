package com.sipios.refactoring.controller;


import com.sipios.refactoring.dto.ShoppingDetails;
import com.sipios.refactoring.service.ShoppingService;
import com.sipios.refactoring.utils.enums.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    private ShoppingService shoppingService = new ShoppingService();

    @PostMapping
    public String getPrice(@RequestBody ShoppingDetails s) {
        double p;

        // Calculate total shopping price
        try {
            p = shoppingService.getPrice(s);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Check if shopping price is too high for customer type
        try {
            if (s.getType().equals(CustomerType.STANDARD_CUSTOMER)) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (s.getType().equals(CustomerType.PREMIUM_CUSTOMER)) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (s.getType().equals(CustomerType.PLATINUM_CUSTOMER)) {
                if (p > 2000) {
                    throw new Exception("Price (" + p + ") is too high for platinum customer");
                }
            } else {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
    }
}
