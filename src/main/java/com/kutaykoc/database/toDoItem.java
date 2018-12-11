package com.kutaykoc.database;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

/**
 * Created by kutay on 6.12.2018.
 */
public class toDoItem {
    private String item_id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String item_name;
    private String item_description;
    private String item_status="not";
    private String item_create_date;
    private String item_deadline;
    private List<String> item_dependencies;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_status() {
        return item_status;
    }

    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }

    public String getItem_create_date() {
        return item_create_date;
    }

    public void setItem_create_date(String item_create_date) {
        this.item_create_date = item_create_date;
    }

    public String getItem_deadline() {
        return item_deadline;
    }

    public void setItem_deadline(String item_deadline) {
        this.item_deadline = item_deadline;
    }

    public List<String> getItem_dependencies() {
        return item_dependencies;
    }

    public void setItem_dependencies(List<String> item_dependencies) {
        this.item_dependencies = item_dependencies;
    }
}
