package com.itcode.service;

import com.itcode.entity.Order;

public interface RuleService {

    Order executeScope(Order order);
}
