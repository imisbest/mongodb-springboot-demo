package com.csw.mongodbspringbootdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class AllTypes {
    @Id// 主键类型只能为：String，ObjectId,BigInteger
    @Indexed
    private String id;
    private String string;
    private Date date;
    private Integer anInt;
    private Double aDouble;
    private Boolean aBoolean;
}
