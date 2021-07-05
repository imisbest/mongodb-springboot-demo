package com.csw.mongodbspringbootdemo.repository;

import com.csw.mongodbspringbootdemo.entity.Clazz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClazzRepository extends MongoRepository<Clazz, String> {/*第二种写法*/

}
