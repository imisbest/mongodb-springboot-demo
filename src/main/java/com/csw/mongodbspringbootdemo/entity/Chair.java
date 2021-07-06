package com.csw.mongodbspringbootdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Chair {
    private String id;
    private String unitCode;
    private String name;
    private String lastCode;//模拟标签
}
