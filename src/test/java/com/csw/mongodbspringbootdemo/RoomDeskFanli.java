package com.csw.mongodbspringbootdemo;

import com.csw.mongodbspringbootdemo.entity.Desk;
import com.csw.mongodbspringbootdemo.entity.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoomDeskFanli {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void saveRoom() {//添加房间
        Room room = new Room();
        room.setName("房间");
        mongoTemplate.save(room);
        Room room2 = new Room();
        room2.setName("漂亮房间");
        mongoTemplate.save(room2);

    }


    @Test
    public void saveDesk() {//添加桌子
        String roomName = "漂亮房间";
        String deskName = "4号桌子";
        Query query = new Query(Criteria.where("name").is(roomName));
        Room room = mongoTemplate.findOne(query, Room.class);
        Desk desk = new Desk();
        desk.setName(deskName);
        assert room != null;
        desk.setRoomId(room.getId());
        mongoTemplate.save(desk);
        System.out.println(room);
        Query query2 = new Query(Criteria.where("name").is(deskName));
        Desk desk2 = mongoTemplate.findOne(query2, Desk.class);
        System.out.println(desk2);
        System.out.println(room.getId().equals(desk2.getRoomId()));
    }
    @Test
    public void findMoreTable() {//多表联查

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("room"). //关联表名
                localField("roomId"). //主关联字段
                foreignField("_id").//从表关联字段对应的次表字段
                as("ClazzStudents");//查询结果集合名
//
        //
        Aggregation aggregation = Aggregation.newAggregation(/*matchZi,*/lookupOperation);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "desk", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }

   /* @Test
    public void search() {
        LookupOperation lookupOperation = LookupOperation.newLookup().from("DirectTrainPostLeaveMessageComment")   //从表名
                .localField("_id")   //主表关联字段
                .foreignField("leaveId")   //从表关联字段
                .as("CommentList");   //查询结果名
        //匹配id条件
        MatchOperation matchOperation = new MatchOperation(Criteria.where("postId").is(postId));
        //按回帖时间排序
        SortOperation sortOperation = new SortOperation(Sort.by(Sort.Order.desc("leaveMessageTime")));
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);
        List<Map> result = mongoTemplate.aggregate(aggregation, "DirectTrainPostLeaveMessage", Map.class).getMappedResults();
        //DirectTrainPostLeaveMessage是主表名


    }*/
}
