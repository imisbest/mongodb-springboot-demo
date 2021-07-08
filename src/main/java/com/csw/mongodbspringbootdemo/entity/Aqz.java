package com.csw.mongodbspringbootdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Document(collection = "aqz")
public class Aqz {
    @Id
    private String id;
    @Field("now_Date")
    private Date createDate;
    private String string;
    private Integer anInt;
    private Double aDouble;
    private Boolean aBoolean;
    private List list;
}
