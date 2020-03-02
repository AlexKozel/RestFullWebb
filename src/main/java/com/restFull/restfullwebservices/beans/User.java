package com.restFull.restfullwebservices.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@ApiModel(description = "All details about user.")
@Entity
public class User  {

    @Id
    @GeneratedValue
    private Integer userId;

    @ApiModelProperty( notes= "Name must be longer then 2")
    @Size(min=2, message = "Name must be longer than 2")
    private String name;

    @Past(message = "Wrong format of date")
    @ApiModelProperty(notes = "Date should be in the past")
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    public User(Integer id, String name, Date birthDate){
       super();
        this.userId = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public User(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}


//    import org.springframework.hateoas.EntityModel;
//            import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
//        EntityModel<User> model = new EntityModel<>(user);
//        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        model.add(linkTo.withRel("all-users"));