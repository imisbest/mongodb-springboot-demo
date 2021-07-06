package com.csw.mongodbspringbootdemo;

import com.csw.mongodbspringbootdemo.entity.Chair;
import com.csw.mongodbspringbootdemo.entity.Desk;
import com.csw.mongodbspringbootdemo.entity.Room;
import com.mongodb.BasicDBObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoomDesk {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void saveRoom() {//添加房间
        Room room = new Room();
        room.setName("空房间2");
        room.setUnitCode(UUID.randomUUID().toString());
        mongoTemplate.save(room);
        Room room2 = new Room();
        room2.setName("空房间1");
        room2.setUnitCode(UUID.randomUUID().toString());
        mongoTemplate.save(room2);


    }


    @Test
    public void saveDesk() {//添加桌子
        String roomName = "光明房间";
        String deskName = "5号桌子";
        Query query = new Query(Criteria.where("name").is(roomName));
        Room room = mongoTemplate.findOne(query, Room.class);
        Desk desk = new Desk();
        desk.setName(deskName);
        assert room != null;
        desk.setUnitCode(room.getUnitCode());
        mongoTemplate.save(desk);
        System.out.println(room);

        Query query2 = new Query(Criteria.where("name").is(deskName));
        Desk desk2 = mongoTemplate.findOne(query2, Desk.class);
        System.out.println(desk2);

    }

    @Test
    public void groupBy() {//group
        List<AggregationOperation> aggs = new ArrayList<>();
        //aggs.add(Aggregation.match(Criteria.where("name").is("log")));
        aggs.add(Aggregation.group("name").count().as("count"));
        aggs.add(Aggregation.project()
                .and("_id").as("name")
                .and("count").as("count"));

        Aggregation agg = Aggregation.newAggregation(aggs);

        AggregationResults<Map> results = mongoTemplate.aggregate(agg,Desk.class, Map.class);
        for (Map result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void findMoreTable() {//两表联查

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("room"). //关联表名
                localField("unitCode"). //主关联字段
                foreignField("unitCode").//从表关联字段对应的次表字段
                as("rooms");//查询结果集合名
        Criteria ordercri = Criteria.where("rooms").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi = Aggregation.match(ordercri);
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,matchZi);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "desk", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }
    @Test
    public void findMoreTable2() {//两表联查

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("desk"). //关联表名
                localField("unitCode"). //主关联字段
                foreignField("unitCode").//从表关联字段对应的次表字段
                as("desks");//查询结果集合名

        Criteria ordercri = Criteria.where("desks").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi = Aggregation.match(ordercri);
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,matchZi);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "room", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }


    @Test
    public void findMoreTableZongHe() {//两表联查

        int pageNumber = 2;//0,1相同
        int pageSize = 2;
        //拼装关联信息
        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("room"). //关联表名
                localField("unitCode"). //主关联字段
                foreignField("unitCode").//从表关联字段对应的次表字段
                as("ClazzStudents");//查询结果集合名
        //拼装具体查询信息
        //次表
        Criteria ordercri = Criteria.where("ClazzStudents").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi = Aggregation.match(ordercri);
        //主表
        Criteria qqq = Criteria.where("name").regex("号");//只查询名字中带有文的人
        AggregationOperation matchFu = Aggregation.match(qqq);
        //分页查询
        Aggregation aggregation = Aggregation.newAggregation(matchFu, lookupOperation, matchZi,
                Aggregation.sort(Sort.Direction.DESC,"name")
        , Aggregation.skip(pageSize>1?(pageNumber-1)*pageSize:0)
        ,Aggregation.limit(pageSize));//排序 Aggregation.skip(pageable.getPageNumber()>1?(pageable.getPageNumber()-1)*pageable.getPageSize():0),//pagenumber
        //分页
      /*  Aggregation.skip(pageSize>1?(pageNumber-1)*pageSize:0);
        Aggregation.limit(pageSize);*/
        //Aggregation.group("name");

        //总数查询
        Aggregation counts = Aggregation.newAggregation(matchFu, lookupOperation, matchZi);
         int count = mongoTemplate.aggregate(counts, Desk.class, BasicDBObject.class).getMappedResults().size();
        System.out.println("【count】"+count);
        List<Map> results = mongoTemplate.aggregate(aggregation, "desk", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }
    /*public PageResult<T> pagination(Class<T> clazz, int pageSize, int pageNum, Query query) {
        long total = this.mongoTemplate.count(query, clazz);
        Integer pages = (int)Math.ceil((double)total / (double)pageSize);
        if (pageNum <= 0 || pageNum > pages) {
            pageNum = 1;
        }
        int skip = pageSize * (pageNum - 1);
        query.skip(skip).limit(pageSize);
        List<T> list = mongoTemplate.find(query, clazz);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setPages(pages);
        pageResult.setPageSize(pageSize);
        pageResult.setPageNum(pageNum);
        pageResult.setList(list);
        return pageResult;
    }
*/
    @Test
    public void saveChair() {//添加椅子
        String roomName = "光明房间";
        String chairName = "1号椅子";
        Query query = new Query(Criteria.where("name").is(roomName));
        Room room = mongoTemplate.findOne(query, Room.class);
        Chair chair = new Chair();
        chair.setName(chairName);
        assert room != null;
        chair.setUnitCode(room.getUnitCode());
        mongoTemplate.save(chair);

    }
    @Test
    public void findMoreTable3_0() {//三表联查(相同关联字段)

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("desk"). //关联表名
                localField("unitCode"). //主关联字段
                foreignField("unitCode").//从表关联字段对应的次表字段
                as("desks");//查询结果集合名

        Criteria ordercri = Criteria.where("desks").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi = Aggregation.match(ordercri);
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("chair"). //关联表名
                localField("unitCode"). //主关联字段
                foreignField("unitCode").//从表关联字段对应的次表字段
                as("chairs");//查询结果集合名

        Criteria ordercri2 = Criteria.where("chairs").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi2 = Aggregation.match(ordercri2);
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,matchZi,lookupOperation2,matchZi2);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "room", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }

    //数据模拟
    @Test
    public void findMoreTable3_1() {//三表联查(不同关联字段)经过测试，多表联查只能使用同一个关联字段，from相当于只能left join最外层的那个表

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("desk"). //关联表名
                localField("unitCode"). //主关联字段
                foreignField("unitCode").//从表关联字段对应的次表字段
                as("desks");//查询结果集合名

        Criteria ordercri = Criteria.where("desks").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi = Aggregation.match(ordercri);
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("chair"). //关联表名
                localField("lastCode"). //主关联字段
                foreignField("lastCode").//从表关联字段对应的次表字段
                as("chairs");//查询结果集合名

        Criteria ordercri2 = Criteria.where("chairs").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi2 = Aggregation.match(ordercri2);
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,matchZi,lookupOperation2,matchZi2);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "room", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }

}
