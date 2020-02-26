package com.restFull.restfullwebservices.dao;

import java.util.Date;
import java.util.Iterator;
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
            if(user.getUserId() == id){
              return user;
            }
        }
        return null;
    }

    public User save( User user){
        if(user.getUserId()==null){
            user.setUserId(++count);
        }
        users.add(user);
        return user;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUserId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }


}
