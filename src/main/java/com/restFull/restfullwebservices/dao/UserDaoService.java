package com.restFull.restfullwebservices.dao;

import java.util.Date;
import java.util.List;
import com.restFull.restfullwebservices.beans.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static Integer count =3;


    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Eve", new Date()));
        users.add(new User(3, "Jack", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id){
        for (User user: users ) {
            if(user.getId()==id){
             return user;
            }
        }return null;
    }

    public User save( User user){
        if(user.getId()==null){
            user.setId(++count);
        }
        users.add(user);
        return user;
    }

}
