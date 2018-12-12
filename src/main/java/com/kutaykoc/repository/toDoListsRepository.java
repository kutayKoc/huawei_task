package com.kutaykoc.repository;

import com.kutaykoc.database.toDoLists;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


/**
 * Created by kutay on 8.12.2018.
 */
public interface toDoListsRepository extends MongoRepository<toDoLists,String> {

    @Query(value = "{_id:?0}")
    toDoLists findBy_id(String _id);

}
