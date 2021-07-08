package com.csw.mongodbspringbootdemo;

import com.csw.mongodbspringbootdemo.entity.AllTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AllTypesTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void insertOneTest() {
        AllTypes allTypes = new AllTypes();
        allTypes.setString("你是谁");
        allTypes.setDate(new Date());
        allTypes.setAnInt(111);
        allTypes.setADouble(2.2);
        allTypes.setABoolean(true);

        mongoTemplate.save(allTypes);
    }
}
