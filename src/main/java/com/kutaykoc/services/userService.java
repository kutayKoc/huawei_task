package com.kutaykoc.services;


import com.kutaykoc.database.toDoLists;
import com.kutaykoc.database.user;
import com.kutaykoc.repository.UserRepository;
import com.kutaykoc.repository.toDoListsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by kutay on 9.12.2018.
 */
@Service
public class userService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private toDoListsRepository listsRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<user> getAllUser(){
        return this.userRepository.findAll();
    }

    public user createUser(user theuser){
        return this.userRepository.save(theuser);
    }

    public List<user> findUserFromNameRegex(String name){
        return this.userRepository.findByNameRegexp(name);
    }

    public void deleteUserById(String _id){
        user data=userRepository.returnToDoListsIds(_id);
        System.out.println(data.getToDoLists());
            mongoTemplate.remove(
                    new Query(Criteria.where("_id").in(data.getToDoLists())), toDoLists.class
            );
        this.userRepository.deleteById(_id);
    }

    public user userCheckLogin(String user_email,String user_password){

        user data=mongoTemplate.findOne(Query.query(Criteria.where("user_email").is(user_email).and("user_password").is(user_password)),user.class);
        return data;
    }





}
