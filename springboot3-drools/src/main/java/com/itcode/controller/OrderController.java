package com.itcode.controller;

import com.itcode.entity.Order;
import com.itcode.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("order")
@RestController()
public class OrderController {

    @Autowired
    RuleService ruleService;

    @GetMapping("/score")
    public Order score(@RequestParam int amount) {
        log.info("~~~ start to get scope by amount:" + amount);
        Order order = new Order();
        order.setAmount(amount);
        Order result = ruleService.executeScope(order);
        log.info("~~~ order info:" + result);
        return result;
    }
}
