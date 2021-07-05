package com.csw.mongodbspringbootdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Document(collection = "t_student")
public class Student {
    private String id;
    @Field("name")
    private String studentName;
    private int age;
    @Field("clazz_id")
    private String clazzId;
}
