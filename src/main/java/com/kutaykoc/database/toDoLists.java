package com.kutaykoc.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutay on 6.12.2018.
 */
@Document(collection = "toDoListsData")
public class toDoLists {
    @Id
    private String _id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String list_name;
    private String list_create_date;
    private List<toDoItem> toDoItems;

    protected toDoLists(){this.toDoItems=new ArrayList<>();}


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public String getList_create_date() {
        return list_create_date;
    }

    public void setList_create_date(String list_create_date) {
        this.list_create_date = list_create_date;
    }

    public List<toDoItem> getToDoItems() {
        return toDoItems;
    }

    public void setToDoItems(List<toDoItem> toDoItems) {
        this.toDoItems = toDoItems;
    }
}
