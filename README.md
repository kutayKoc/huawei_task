
## Installation
Clone or download

## Example reguest

https://yourdomain/api/user/all
>you have to add '/api/' for request 


## Functions
```
@RequestMapping(value="/user/all", method= RequestMethod.GET)
public void getAllUser()

get all user from database
Request method is GET
```

```
@RequestMapping(value="/toDoLists/all",method=RequestMethod.GET)
public void getAllLists()

get all lists from datebase
Request method is GET
```

```
@RequestMapping(value="/query/user/insert",method=RequestMethod.PUT)
public void insertUser(@RequestBody user)

create new user using 'user' model.. this function waiting user model json data
Request method is PUT
body example
{
	"user_firstName":"clarke",
	"user_lastName":"kent",
	"user_password":"123456",
	"user_email":"superboy@outlook.com",
	"user_nickname":"clarkelouis"
}
```


```
@RequestMapping(value="/query/user/{name}",method=RequestMethod.GET)
public void getUserFromName(@PathVariable("name") String name)

get user from name
Request method is GET
```

```
@RequestMapping(value = "/query/user/login/check",method = RequestMethod.POST)
public void checkUserLoginData(@RequestBody user theuser)

check user login . this function waiting user model json data
Request method is POST
```

```
@RequestMapping(value="/query/toDoLists/toDoItems/insert/{_id}",method=RequestMethod.PUT)
public void inserttoDoItemsTotoDoLists(@PathVariable("_id") String _id,@RequestBody toDoItem theToDoItems)

create new item to list. this function waiting item model json data
Request method is PUT

Body example
 {
    "list_name":"kutay yapılacaklar",
    "list_create_date":"10.10.2018"
 }
```



```
@RequestMapping(value="/query/toDoItems/toDoItems/update/{item_id}",method=RequestMethod.POST)
public void updateToDoItems(@PathVariable("item_id")String item_id,@RequestBody toDoItem theToDoItem)

update item with id. this function waiting item model json data
Request method is POST
Body example 

{
	"item_name":"kurulum6",
	"item_description":"java ve diger kurulumlar yapilacak6"
}
```


```
@RequestMapping(value="/query/toDoLists/update/{list_id}",method=RequestMethod.POST)
public void updateToDoListsElement(@PathVariable("list_id") String list_id,@RequestBody toDoLists theToDoLists)

update list element with id. this function waiting list model json data
Request method is POST
Body Example

{
	"list_name":"test list name"
}

```



```
@RequestMapping(value="/query/user/delete/{_id}",method=RequestMethod.DELETE)
public void deleteUserWithId(@PathVariable("_id") String _id)


delete user with id.
Request method is DELETE
```


```
@RequestMapping(value="/query/toDoLists/delete/{user_id}/{_id}",method=RequestMethod.DELETE)
public void deleteToDoLists(@PathVariable("_id") String _id,@PathVariable("user_id")String user_id)

delete list with id.
Request method is DELETE
```



```
@RequestMapping(value="/query/toDoItems/dependencies/insert/{_id}",method=RequestMethod.PUT)
public void insertDependenciesToItem(@PathVariable("_id")String _id,String item_id)

add dependencies id to item with id. item_id is insertin _id
Request method is PUT
```


```
@RequestMapping(value="/query/toDoItems/dependencies/delete/{_id}",method=RequestMethod.DELETE)
public void deleteDependenciesFromItems(@PathVariable("_id")String _id,String item_id)

delete dependencies id to item with id. item_id is deleting _id
Request method is DELETE
```


```
@RequestMapping(value="/query/toDoItems/dependencies/list/{_id}",method = RequestMethod.GET)
public void listDependenciesFromItems(@PathVariable("_id")String _id)

List dependencies id to item with id.
Request method is GET
```



