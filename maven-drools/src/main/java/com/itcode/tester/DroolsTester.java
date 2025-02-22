package com.itcode.tester;

import com.itcode.entity.Order;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTester {

    @Test
    public void test01() {
        KieServices kieServices = KieServices.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession();

        Order order = new Order();
        order.setAmount(1999);
        //封装参数
        kieSession.insert(order);
        kieSession.fireAllRules();
        System.out.println(order);
        kieSession.dispose();

    }
}
