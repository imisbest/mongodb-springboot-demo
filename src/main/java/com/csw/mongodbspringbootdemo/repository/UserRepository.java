package com.csw.mongodbspringbootdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.csw.mongodbspringbootdemo.model.User;

public interface UserRepository extends MongoRepository<User, String> {/*第二种写法*/

	public User findOneByName(String name);

}
