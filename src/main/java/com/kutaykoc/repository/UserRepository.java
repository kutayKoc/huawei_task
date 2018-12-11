package com.kutaykoc.repository;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kutaykoc.database.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutay on 6.12.2018.
 */
public interface UserRepository extends MongoRepository<user,String> {
    @Query("{user_firstName: { $regex: ?0 } }")
    List<user> findByNameRegexp(String name);

    @Query(value = "{_id:?0}")
    user returnToDoListsIds(String _id);
}
