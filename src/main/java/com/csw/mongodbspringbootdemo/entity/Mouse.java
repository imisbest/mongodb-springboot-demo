package com.csw.mongodbspringbootdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Mouse {
    @Id
    private ObjectId id;
    private String name;
    private ObjectId computerId;
}
