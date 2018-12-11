package com.kutaykoc.repository;

import com.kutaykoc.database.toDoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by kutay on 9.12.2018.
 */

public interface toDoItemsRepository extends MongoRepository<toDoItem,String> {
    @Query(value="{toDoItems.item_id:?0},{_id:0,toDoItems:{$elemMatch:{item_id:?0}}}")
    toDoItem getListByToDoItemsId(String item_id);
}
