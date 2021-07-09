package com.csw.mongodbspringbootdemo;

import com.csw.mongodbspringbootdemo.entity.Clazz;
import com.csw.mongodbspringbootdemo.entity.Student;
import com.csw.mongodbspringbootdemo.repository.ClazzRepository;
import com.csw.mongodbspringbootdemo.repository.UserRepository;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClazzStudentTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClazzRepository clazzRepository;

    @Test
    public void saveClazz() {//添加课堂
        Clazz clazz = new Clazz();
        clazz.setClazzName("天天课堂3");
        mongoTemplate.insert(clazz);
    }

    @Test
    public void saveStudent() {//添加学生
        String clazzName = "天天课堂";
        Query query = new Query(Criteria.where("clazzName").is(clazzName));
        Clazz clazz = mongoTemplate.findOne(query, Clazz.class);
        Student student = new Student();
        student.setStudentName("小红2");
        student.setAge(18);
        student.setClazzId(clazz.getId());
        mongoTemplate.save(student);

    }

    @Test
    public void findOneClazz() {
        Query query = new Query(Criteria.where("_id").is("60e10203d31e2641ecb748ee"));
        Clazz clazz = mongoTemplate.findOne(query, Clazz.class);
        //clazz】】】" + clazz);
    }

    @Test
    public void findByManyStudent1() {//多条件1
        Query query = new Query();
        query.addCriteria(Criteria.where("studentName").is("小红"));
        query.addCriteria(Criteria.where("age").is(18));
        List<Student> students = mongoTemplate.find(query, Student.class);
        //students);
    }

    @Test
    public void findByManyStudent0() {//多条件0
        Query query = new Query(Criteria.where("studentName").is("小红"));
        query.addCriteria(Criteria.where("age").is(18));
        List<Student> students = mongoTemplate.find(query, Student.class);
        //students);
    }


    @Test
    public void findByManyStudent3() {//多条件3
        Query query = new Query(Criteria.where("studentName").is("小红").and("age").is(18));
        List<Student> students = mongoTemplate.find(query, Student.class);
        //students);
    }

    @Test
    public void findByManyStudent2() {//多条件2
        Student student = new Student();
        student.setStudentName("小红");
        student.setAge(18);
        Query query = new Query(Criteria.byExample(student));
        List<Student> students = mongoTemplate.find(query, Student.class);
        //students);
    }

    @Test
    public void findByManyStudentOr() {//多条件0  or
        Query query = new Query(Criteria.where("studentName").regex("小"));
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("age").is(18),
                        new Criteria().andOperator(Criteria.where("studentName").is("小明"),Criteria.where("age").is(16))
                ));
        List<Student> students = mongoTemplate.find(query, Student.class);
        //students);
    }

    /*query.addCriteria(

            new Criteria().orOperator(

           Criteria.where("value").gte(300),

 new Criteria().andOperator(

           Criteria.where("state1").is(11),

 Criteria.where("state2").is(22)

 )

         )

         );

————————————————
   版权声明：本文为CSDN博主「天武我非」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
   原文链接：https://blog.csdn.net/tjbsl/article/details/80620303*/
    @Test
    public void searchClazzByIn() {//in操作
        List<String> str = new ArrayList<>();
        str.add("天天课堂");
        Query query = new Query(Criteria.where("name").in(str));
        List<Clazz> clazzList = mongoTemplate.find(query, Clazz.class);
        //clazzList】】】" + clazzList);

    }




    @Test
    public void findListStudentSort() {//排序
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "age"));
        List<Student> students = mongoTemplate.find(query, Student.class);
        //students】】】" + students);


    }

    @Test
    public void findFenYeList() {//分页
        //设置分页参数
        Query query = new Query();
        int currentPage = 2;//0,1相同
        int pageSize = 2;
        //设置分页信息
        query.limit(pageSize);
        query.skip(pageSize * (currentPage - 1));
        // query.addCriteria(Criteria.where("clazzName").regex("天"));
        List<Clazz> clazzes = mongoTemplate.find(query, Clazz.class);
        //clazzs】】】" + clazzes);
    }

    @Test
    public void update() {//更新
        Query query = Query.query(Criteria.where("id").is("60e103fcd31e2615bcaf91ed"));//添加查询条件
        //query.addCriteria(Criteria.where("time").gte(beginTime).lte(endTime))；
        Update update = new Update();
        update.set("clazzName", "111");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Clazz.class);
        //updateResult.toString());

    }

    @Test
    public void delete() {//删除
        Query query = Query.query(Criteria.where("id").is("60e103fcd31e2615bcaf91ed"));
        mongoTemplate.remove(query, Clazz.class);
    }

    @Test
    public void findZongHe() {//分页+范围+模糊查询+排序
        //拼装查询信息
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gte(6).lte(18));
        query.with(Sort.by(Sort.Direction.ASC, "age"));
        query.addCriteria(Criteria.where("name").regex("小"));
        //模糊查询名字
        Long count = mongoTemplate.count(query, Student.class);
        //查询总记录数
        List<Student> list = mongoTemplate.find(query, Student.class);

    }


    @Test
    public void findMoreTable1_2() {//多表联查//反例

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("t_clazz"). //关联表名
                localField("_id"). //主关联字段
                foreignField("clazzId").//从表关联字段对应的次表字段
                as("StudentClazzs");//查询结果集合名

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "t_student", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }
    @Test
    public void findMoreTableFan() {//多表联查//反例

        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("t_student"). //关联表名
                localField("id"). //主关联字段
                foreignField("clazzId").//从表关联字段对应的次表字段
                as("StudentClazz");//查询结果集合名
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);//排序
        List<Map> results = mongoTemplate.aggregate(aggregation, "t_clazz", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void findMoreTable2() {//
        //mongodb中有两个表，一个是人物表 一个是宠物表，一个人可以有多个宠物
        //人物表字段为 String id, Integer age,String remark;
        //宠物表字段为 String id, String manId,String age,String remark;
        //拼装分页信息
       /* int currentPage = 1;//0,1相同
        int pageSize = 2;*/
        //拼装关联信息
        LookupOperation lookupOperation = LookupOperation.newLookup().from("t_clazz"). //关联表名
                localField("id"). //主关联字段
                foreignField("clazzId").//从表关联字段对应的次表字段
                as("students");//查询结果集合名
        //拼装具体查询信息
        //次表
       /* Criteria ordercri = Criteria.where("students").not().size(0);//只查询有宠物的人
        // ordercri.and("age").gte(1).lte(5);//只查询1岁到5岁的宠物
        AggregationOperation matchZi = Aggregation.match(ordercri);*/
        //主表
       /* Criteria qqq = Criteria.where("name").regex("天");//只查询名字中带有文的人
        //Criteria qqq = new Criteria();
        AggregationOperation matchFu = Aggregation.match(qqq);*/
        //分页查询
        Aggregation aggregation = Aggregation.newAggregation(/*matchFu, */lookupOperation/*, matchZi*//*, Aggregation.sort(new Sort(Sort.Direction.DESC,"studentName"))*/);//排序 Aggregation.skip(pageable.getPageNumber()>1?(pageable.getPageNumber()-1)*pageable.getPageSize():0),//pagenumber
       /* Aggregation.limit(pageSize);//pagesize
        Aggregation.skip(pageSize * (currentPage - 1));
        Aggregation.group("studentName");*/

        //总数查询
        //Aggregation a = Aggregation.newAggregation(/*matchFu,*/ lookupOperation/*, matchZi*/);
        // int count = mongoTemplate.aggregate(counts, Clazz.class, BasicDBObject.class).getMappedResults().size();
        List<Map> results = mongoTemplate.aggregate(aggregation, "t_student", Map.class).getMappedResults(); //查询出的结果集为BasicDBObject类型
        for (Map result : results) {
            System.out.println(result);
        }
        /*//解析过程
        for (BasicDBObject b : results) {
            //转化为jsonobject对象
            JSONObject jsonObject = new JSONObject(b);
            String id = jsonObject.get("id").toString();
            Integer age = ((int) jsonObject.get("age"));
            String remark = jsonObject.get("remark").toString();
            //转化为jsonarray
            JSONArray dogs = jsonObject.getJSONArray("dogs");
            if (dogs.size() > 0) {
                for (int i = 0; i < dogs.size(); i++) {
                    JSONObject job = dogs.getJSONObject(i);
                    String dogId = job.get("id").toString();
                    String manId = job.get("manId").toString();
                }
            }
        }*/
    }
}
