package com.kutaykoc.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutay on 6.12.2018.
 */
@Document(collection = "user")
public class user {
    @Id
    private String _id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String user_firstName;
    private String user_lastName;
    private String user_password;
    @NotEmpty(message = "Nick name can not be empty")
    @Indexed(unique = true)
    private String user_nickname;
    @Email(message = "invalid email")
    @Indexed(unique = true)
    private String user_email;
    private List<String> toDoLists;

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    protected user(){this.toDoLists=new ArrayList<>();}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_firstName() {
        return user_firstName;
    }

    public void setUser_firstName(String user_firstName) {
        this.user_firstName = user_firstName;
    }

    public String getUser_lastName() {
        return user_lastName;
    }

    public void setUser_lastName(String user_lastName) {
        this.user_lastName = user_lastName;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public List<String> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(List<String> toDoLists) {
        this.toDoLists = toDoLists;
    }
}
