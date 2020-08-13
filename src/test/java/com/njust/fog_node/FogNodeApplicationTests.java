package com.njust.fog_node;

import com.njust.fog_node.dataprocessor.DataContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FogNodeApplicationTests {

    @Autowired
    DataContainer d1;

    @Autowired
    DataContainer d2;

    @Test
    void contextLoads() {
    }

    @Test
    void TestSingleton(){
        System.out.println(d1.equals(d2));
    }

}
