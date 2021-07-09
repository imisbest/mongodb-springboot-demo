package com.csw.mongodbspringbootdemo;

import com.csw.mongodbspringbootdemo.entity.Computer;
import com.csw.mongodbspringbootdemo.entity.Mouse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ComputerMouse {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void saveComputer() {
        Computer computer = new Computer();
        computer.setName("华烁");
        mongoTemplate.save(computer);
        Query query = new Query(Criteria.where("name").is("华烁"));
        Computer computer1 = mongoTemplate.findOne(query, Computer.class);
        Mouse mouse = new Mouse();
        mouse.setName("鼠标1");
        mouse.setComputerId(computer1.getId());
        mongoTemplate.save(mouse);
    }


    @Test
    public void findMoreTable3_0() {//

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("mouse"). //关联表名
                localField("_id"). //主关联字段
                foreignField("computerId").//从表关联字段对应的次表字段
                as("mouses");//查询结果集合名

        Criteria ordercri = Criteria.where("mouses").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi = Aggregation.match(ordercri);

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, matchZi);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "computer", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }
}
