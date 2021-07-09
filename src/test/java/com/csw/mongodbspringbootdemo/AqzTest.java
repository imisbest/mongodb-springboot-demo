package com.csw.mongodbspringbootdemo;

import com.csw.mongodbspringbootdemo.entity.Aqz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AqzTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void save() {
        Aqz aqz = new Aqz();
        aqz.setCreateDate(new Date());
        mongoTemplate.save(aqz);
    }

    @Test
    public void findall() {//存入和查处的时间一致
        List<Aqz> all = mongoTemplate.findAll(Aqz.class);
    }

    @Test
    public void listSave() {//int
        Aqz aqz = new Aqz();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        aqz.setCreateDate(new Date());
        aqz.setList(list);
        mongoTemplate.save(aqz);
    }

    @Test
    public void listStringSave() {//String
        Aqz aqz = new Aqz();
        List list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add(3);
        aqz.setCreateDate(new Date());
        aqz.setList(list);
        mongoTemplate.save(aqz);
    }

    @Test
    public void saveMore() {//一条一条(6836)qps1462
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            Aqz aqz = new Aqz();
            int random = (int) (Math.random() * 2);
            aqz.setABoolean(random == 0 ? false : true);
            aqz.setCreateDate(new Date());
            aqz.setString("string" + i);
            aqz.setAnInt(i);
            aqz.setADouble(Math.random() * 2);
            List list = new ArrayList<>();
            list.add("aaa" + i);
            list.add("bbb" + i);
            list.add("ccc" + i);
            System.out.println(i);
            aqz.setList(list);
            mongoTemplate.save(aqz);
        }
        Long endtTime = System.currentTimeMillis();
        Long resultTime = endtTime - startTime;
        System.out.println("[总共执行耗时]" + resultTime);
    }
    @Test
    public void saveMore2() {//一条一条(6836)qps1462
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            Aqz aqz = new Aqz();
            int random = (int) (Math.random() * 2);
            aqz.setABoolean(random == 0 ? false : true);
            aqz.setCreateDate(new Date());
            aqz.setString("string2" + i);
            aqz.setAnInt(i);
            aqz.setADouble(Math.random() * 2);
            List list = new ArrayList<>();
            list.add("aaa2" + i);
            list.add("bbb2" + i);
            list.add("ccc2" + i);
            System.out.println(i);
            aqz.setList(list);
            mongoTemplate.save(aqz);
        }
        Long endtTime = System.currentTimeMillis();
        Long resultTime = endtTime - startTime;
        System.out.println("[总共执行耗时]" + resultTime);
    }
    @Test
    public void saveMore3() {//一条一条(6836)qps1462
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            Aqz aqz = new Aqz();
            int random = (int) (Math.random() * 2);
            aqz.setABoolean(random == 0 ? false : true);
            aqz.setCreateDate(new Date());
            aqz.setString("string3" + i);
            aqz.setAnInt(i);
            aqz.setADouble(Math.random() * 2);
            List list = new ArrayList<>();
            list.add("aaa3" + i);
            list.add("bbb3" + i);
            list.add("ccc3" + i);
            System.out.println(i);
            aqz.setList(list);
            mongoTemplate.save(aqz);
        }
        Long endtTime = System.currentTimeMillis();
        Long resultTime = endtTime - startTime;
        System.out.println("[总共执行耗时]" + resultTime);
    }
    @Test
    public void saveMore4() {//一条一条(6836)qps1462
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            Aqz aqz = new Aqz();
            int random = (int) (Math.random() * 2);
            aqz.setABoolean(random == 0 ? false : true);
            aqz.setCreateDate(new Date());
            aqz.setString("string4" + i);
            aqz.setAnInt(i);
            aqz.setADouble(Math.random() * 2);
            List list = new ArrayList<>();
            list.add("aaa4" + i);
            list.add("bbb4" + i);
            list.add("ccc4" + i);
            System.out.println(i);
            aqz.setList(list);
            mongoTemplate.save(aqz);
        }
        Long endtTime = System.currentTimeMillis();
        Long resultTime = endtTime - startTime;
        System.out.println("[总共执行耗时]" + resultTime);
    }
    @Test
    public void saveMore5() {//一条一条(6836)qps1462/////两个索引8650
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Aqz aqz = new Aqz();
            int random = (int) (Math.random() * 2);
            aqz.setABoolean(random == 0 ? false : true);
            aqz.setCreateDate(new Date());
            aqz.setString("string5" + i);
            aqz.setAnInt(i);
            aqz.setADouble(Math.random() * 2);
            List list = new ArrayList<>();
            list.add("aaa5" + i);
            list.add("bbb5" + i);
            list.add("ccc5" + i);
            System.out.println(i);
            aqz.setList(list);
            mongoTemplate.save(aqz);
        }
        Long endtTime = System.currentTimeMillis();
        Long resultTime = endtTime - startTime;
        System.out.println("[总共执行耗时]" + resultTime);
    }
    @Test
    public void saveMany() {//批处理(10万条数据1127)（100万条条数据测试75773）qps13197
        Long startTime = System.currentTimeMillis();
        List<Aqz> aqzList = new ArrayList<>();
        for (int i = 0; i <1000000 ; i++) {
            Aqz aqz = new Aqz();
            int random = (int) (Math.random() * 2);
            aqz.setABoolean(random == 0 ? false : true);
            aqz.setCreateDate(new Date());
            aqz.setString("string2" + i);
            aqz.setAnInt(i+20000);
            aqz.setADouble(Math.random() * 2);
            List list = new ArrayList<>();
            list.add("aaa2" + i);
            list.add("bbb2" + i);
            list.add("ccc2" + i);
            aqz.setList(list);
            aqzList.add(aqz);
            System.out.println(i);
        }
        mongoTemplate.insertAll(aqzList);

        Long endtTime = System.currentTimeMillis();
        Long resultTime = endtTime - startTime;
        System.out.println("[总共执行耗时]" + resultTime);

    }
    @Test
    public void findOne() {//存入和查处的时间一致
        Long startTime = System.currentTimeMillis();
        Query query = new Query();
        query.addCriteria(Criteria.where("string").is("string2555"));
        List<Aqz> all = mongoTemplate.find(query, Aqz.class);
        System.out.println(all.size());
        Long endtTime = System.currentTimeMillis();
        Long resultTime = endtTime - startTime;
        System.out.println("[总共执行耗时]" + resultTime);
    }

    @Test
    public void UUid() {
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString());
        //82243279-664d-4652-b982-0b530ae44e1e
        //a0e070dba9fe
    }
}
