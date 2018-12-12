package com.kutaykoc.services;

import com.kutaykoc.database.toDoItem;
import com.kutaykoc.database.toDoLists;
import com.kutaykoc.database.user;
import com.kutaykoc.repository.UserRepository;
import com.kutaykoc.repository.toDoListsRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by kutay on 9.12.2018.
 */
@Service
public class ToDoListsService {
    @Autowired
    private toDoListsRepository listsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<toDoLists> getAlltoDoLists(){

        return this.listsRepository.findAll();
    }

    public toDoLists getListFromUserId(String _id){
        System.out.println(_id);
       return  this.listsRepository.findBy_id(_id);
    }
    public toDoLists createToDoList(toDoLists theToDoLists){
        return this.listsRepository.save(theToDoLists);

    }

    public UpdateResult addItemToList(String _id, toDoItem theToDoItem){
        UpdateResult updateResult=mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(_id)),
                new Update().push("toDoItems",theToDoItem),toDoLists.class);
        return updateResult;
    }

   public UpdateResult updateToDoItem(String item_id,toDoItem theToDoItem){
        UpdateResult wr;
        if(theToDoItem.getItem_name()!=null){
            Criteria criteria=Criteria.where("toDoItems.item_id").is(item_id);
            Update update=new Update().set("toDoItems.$.item_name", theToDoItem.getItem_name());
             wr=mongoTemplate.updateMulti(new Query(criteria),update,toDoLists.class);
        }else if(theToDoItem.getItem_create_date()!=null){
            Criteria criteria=Criteria.where("toDoItems.item_id").is(item_id);
            Update update=new Update().set("toDoItems.$.item_create_date", theToDoItem.getItem_name());
             wr=mongoTemplate.updateMulti(new Query(criteria),update,toDoLists.class);
        }else if(theToDoItem.getItem_deadline()!=null){
            Criteria criteria=Criteria.where("toDoItems.item_id").is(item_id);
            Update update=new Update().set("toDoItems.$.item_deadline", theToDoItem.getItem_name());
             wr=mongoTemplate.updateMulti(new Query(criteria),update,toDoLists.class);
        }else if(theToDoItem.getItem_description()!=null){
            Criteria criteria=Criteria.where("toDoItems.item_id").is(item_id);
            Update update=new Update().set("toDoItems.$.item_description", theToDoItem.getItem_name());
             wr=mongoTemplate.updateMulti(new Query(criteria),update,toDoLists.class);
        }else if(theToDoItem.getItem_status()!=null){
            Criteria criteria=Criteria.where("toDoItems.item_id").is(item_id);
            Update update=new Update().set("toDoItems.$.item_status", theToDoItem.getItem_name());
             wr=mongoTemplate.updateMulti(new Query(criteria),update,toDoLists.class);
        }else{
            wr=null;
        }

       return wr;
   }
   public UpdateResult updateToDoLists(String list_id,toDoLists theToDoLists){
       UpdateResult wr;
       if(theToDoLists.getList_name()!=null){
           Criteria criteria=Criteria.where("_id").is(list_id);
           Update update=new Update().set("list_name",theToDoLists.getList_name());
           wr=mongoTemplate.updateMulti(new Query(criteria),update,toDoLists.class);
       }else{
           wr=null;
       }
       return wr;
   }

   public void deleteListById(String _id,String user_id){
       //delete List id from user collection
       Criteria criteria=Criteria.where("_id").is(user_id);
       Update update=new Update().pull("toDoLists",_id);
       mongoTemplate.updateFirst(new Query(criteria),update,user.class);
       this.listsRepository.deleteById(_id);
   }

   public UpdateResult deleteItemFromList(String item_id){
       Update updateObj = new Update();
       updateObj.pull("toDoItems", new BasicDBObject("item_id",item_id));
       Criteria criteria=Criteria.where("toDoItems.item_id").is(item_id);
       UpdateResult updateResult= mongoTemplate.updateFirst(new Query(criteria),updateObj,"toDoListsData");
       return  updateResult;
   }

   public UpdateResult insertItemIdToDependencies(String _id,String item_id){
       UpdateResult updateResult=mongoTemplate.updateFirst(
               Query.query(Criteria.where("toDoItems.item_id").is(_id)),
               new Update().push("toDoItems.$.item_dependencies",item_id),toDoLists.class);
       return updateResult;
   }

   public UpdateResult deleteItemIdFromDependencies(String _id,String item_id){
       UpdateResult updateResult=mongoTemplate.updateFirst(
               Query.query(Criteria.where("toDoItems.item_id").is(_id)),
               new Update().pull("toDoItems.$.item_dependencies",item_id),toDoLists.class);
       return updateResult;
   }

   public ArrayList getDependencies(String _id){
       Aggregation agg=Aggregation.newAggregation(
               Aggregation.unwind("$toDoItems"),
               Aggregation.match(Criteria.where("toDoItems.item_id").is(_id)),
               Aggregation.replaceRoot("$toDoItems")
       );
       AggregationResults<toDoItem> data=mongoTemplate.aggregate(agg,"toDoListsData",toDoItem.class);
       ArrayList result;
       toDoItem var= (toDoItem) data.getMappedResults().toArray()[0];
       var.getItem_dependencies();
       return (ArrayList) var.getItem_dependencies();
   }

}
