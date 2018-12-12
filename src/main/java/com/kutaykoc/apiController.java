package com.kutaykoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kutaykoc.database.toDoItem;
import com.kutaykoc.database.toDoLists;
import com.kutaykoc.database.user;
import com.kutaykoc.services.ToDoListsService;
import com.kutaykoc.services.userService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutay on 6.12.2018.
 */
@RestController
@RequestMapping("/api")
public class apiController {

    @Autowired
    private userService userService;
    @Autowired
    private ToDoListsService listsService;
    @Autowired
    MongoTemplate mongoTemplate;


    //get all user
    @RequestMapping(value="/user/all", method= RequestMethod.GET)
    public void getAllUser(HttpServletResponse response) throws IOException {
        try{
            List<user> data= this.userService.getAllUser();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(data);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //get all lists
    @RequestMapping(value="/toDoLists/all",method=RequestMethod.GET)
    public void getAllLists(HttpServletResponse response) throws IOException {
        try{
            List<toDoLists> data=this.listsService.getAlltoDoLists();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(data);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //get all lists
    @RequestMapping(value="/toDoLists/all/{_id}",method=RequestMethod.GET)
    public void getListsFromUserId(HttpServletResponse response) throws IOException {
        try{
            List<toDoLists> data=this.listsService.getAlltoDoLists();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(data);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }


    //create new user
    @RequestMapping(value="/query/user/insert",method=RequestMethod.PUT)
    public void insertUser(@RequestBody user theUser,HttpServletResponse response) throws IOException {
        try{
            user result=this.userService.createUser(theUser);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(result);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //get user from name
    @RequestMapping(value="/query/user/{name}",method=RequestMethod.GET)
    public void getUserFromName(@PathVariable("name") String name,HttpServletResponse response) throws IOException {
        try{
            List<user> data=this.userService.findUserFromNameRegex(name);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(data);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //check user login
    @RequestMapping(value = "/query/user/login/check",method = RequestMethod.POST)
    public void checkUserLoginData(@RequestBody user theuser,HttpServletResponse response) throws IOException{
        try{
            System.out.println(theuser.getUser_email());
            System.out.println(theuser.getUser_password());
           user data=this.userService.userCheckLogin(theuser.getUser_email(),theuser.getUser_password());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(data);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //insert data to users toDoLists field
    @RequestMapping(value="/query/toDoLists/insert/{_id}",method=RequestMethod.PUT)
    public void insertDataToTodoLists(@PathVariable("_id")String _id,@RequestBody toDoLists theToDoLists,HttpServletResponse response) throws IOException {
       try{
           toDoLists result=this.listsService.createToDoList(theToDoLists);
           //save user id to toDoLists array
           UpdateResult updateResult=mongoTemplate.updateFirst(
                   Query.query(Criteria.where("_id").is(_id)),
                   new Update().push("toDoLists",result.get_id()),user.class);

           ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
           String json = ow.writeValueAsString(result);
           response.setStatus(200);
           response.setHeader("Content-Type","application/json");
           response.getWriter().write(json);
       }catch(Exception e){
           response.setStatus(400);
           response.setHeader("Content-Type","application/text");
           response.getWriter().write(e.toString());
       }
    }

    //insert item to toDoLists array using toDolists _id
    @RequestMapping(value="/query/toDoLists/toDoItems/insert/{_id}",method=RequestMethod.PUT)
    public void inserttoDoItemsTotoDoLists(@PathVariable("_id") String _id,@RequestBody toDoItem theToDoItems,HttpServletResponse response) throws IOException {
        try{
            long time=System.currentTimeMillis();
            theToDoItems.setItem_id(_id+String.valueOf(time));
            UpdateResult updateResult=this.listsService.addItemToList(_id,theToDoItems);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(updateResult);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch (Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //update toDoItems using id
    @RequestMapping(value="/query/toDoItems/toDoItems/update/{item_id}",method=RequestMethod.POST)
    public void updateToDoItems(@PathVariable("item_id")String item_id,@RequestBody toDoItem theToDoItem,HttpServletResponse response) throws IOException {
        try{
            UpdateResult wr=this.listsService.updateToDoItem(item_id,theToDoItem);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(wr);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch (Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //update toDolists element
    @RequestMapping(value="/query/toDoLists/update/{list_id}",method=RequestMethod.POST)
    public void updateToDoListsElement(@PathVariable("list_id") String list_id,@RequestBody toDoLists theToDoLists,HttpServletResponse response) throws IOException {
        try{
            UpdateResult wr=this.listsService.updateToDoLists(list_id,theToDoLists);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(wr);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }


    //delete user from database
    @RequestMapping(value="/query/user/delete/{_id}",method=RequestMethod.DELETE)
    public void deleteUserWithId(@PathVariable("_id") String _id,HttpServletResponse response) throws IOException {
        try{

            this.userService.deleteUserById(_id);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(new BasicDBObject("message",_id + " data is deleted from database").toJson());

        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //delete todoLists from data base
    @RequestMapping(value="/query/toDoLists/delete/{user_id}/{_id}",method=RequestMethod.DELETE)
    public void deleteToDoLists(@PathVariable("_id") String _id,@PathVariable("user_id")String user_id,HttpServletResponse response) throws IOException {
        try{
            this.listsService.deleteListById(_id,user_id);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(new BasicDBObject("message",_id + " data is deleted from database").toJson());
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //delete toDoItems from database
    @RequestMapping(value="/query/toDoLists/toDoItems/delete/{_id}",method=RequestMethod.DELETE)
    public void deleteToDoItems(@PathVariable("_id") String _id,HttpServletResponse response) throws IOException {
        try{
            UpdateResult updateResult=this.listsService.deleteItemFromList(_id);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(updateResult);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //push dependencies to items
    @RequestMapping(value="/query/toDoItems/dependencies/insert/{_id}",method=RequestMethod.PUT)
    public void insertDependenciesToItem(@PathVariable("_id")String _id,HttpServletResponse response,String item_id) throws IOException {
        try{
            UpdateResult updateResult=this.listsService.insertItemIdToDependencies(_id,item_id);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(updateResult);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //delete dependencies from item
    @RequestMapping(value="/query/toDoItems/dependencies/delete/{_id}",method=RequestMethod.DELETE)
    public void deleteDependenciesFromItems(@PathVariable("_id")String _id,String item_id,HttpServletResponse response) throws  IOException{
        try{
            UpdateResult updateResult=this.listsService.deleteItemIdFromDependencies(_id,item_id);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(updateResult);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }

    //get items dependencies
    @RequestMapping(value="/query/toDoItems/dependencies/list/{_id}",method = RequestMethod.GET)
    public void listDependenciesFromItems(@PathVariable("_id")String _id,HttpServletResponse response) throws IOException{
        try{
            ArrayList data=this.listsService.getDependencies(_id);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(data);
            response.setStatus(200);
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(json);
        }catch(Exception e){
            response.setStatus(400);
            response.setHeader("Content-Type","application/text");
            response.getWriter().write(e.toString());
        }
    }
}
