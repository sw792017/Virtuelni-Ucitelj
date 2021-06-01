package com.javatechie.spring.drools.api;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringDroolsApplication.class)
@WebAppConfiguration
public abstract class UnitTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
