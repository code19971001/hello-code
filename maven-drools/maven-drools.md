### Maven with Drools

1.导入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.itcode</groupId>
    <artifactId>springboot3-drools</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <drools.version>10.0.0</drools.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-compiler</artifactId>
            <version>${drools.version}</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-xml-support</artifactId>
            <version>${drools.version}</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-mvel</artifactId>
            <version>${drools.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>2.0.6</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
        </dependency>

    </dependencies>

</project>
```

2.resource/META-INF下定义`kmodule.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<kmodule xmlns="http://www.drools.org/xsd/kmodule">
    <kbase name="kbase-1" packages="rules" default="true">
        <ksession name="session-rule" default="true"/>
    </kbase>
</kmodule>
```

3.定义entity

```java
package com.itcode.entity;



public class Order {

    private int amount;

    private int score;

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Order{" +
                "amount=" + amount +
                ", score=" + score +
                '}';
    }
}

```

4.rules目录下定义drl文件

```drl
package rules;

import com.itcode.entity.Order;
dialect "java"
//100以下不加分
//100-500:加100分
//500-1000:加500分
//1000以上:加金额数分

//规则
rule "score_1"
when
    $order:Order(amount<100)
then
    $order.setScore(0);
end

rule "score_2"
when
    $order:Order(amount>=100&&amount<500)
then
    $order.setScore(100);
end

rule "score_3"
when
    $order:Order(amount>=500&&amount<1000)
then
    $order.setScore(500);
end

rule "score_4"
when
    $order:Order(amount>=1000)
then
    $order.setScore(1000);
end
```

5.类测试

```java
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
```

### Springboot3 wtih drools

1.定义pom文件

