package com.snoopyslist.DBmodels;

/**
 * Created by rui on 4/27/17.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table( name = "USER_SQL")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
        @NamedQuery(name = "User.deleteById", query = "DELETE FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.checkEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.checkUserLogin", query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
                })
public class User  implements Serializable
{

    public enum Type {

        PERSON, SHELTER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 40)
    private String name;

    @Column(length = 40)
    private String lastName;

    @Column(length = 100)
    private String website;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String password;

    @Column(length = 10)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<PetPost> posts;

    @Enumerated(EnumType.STRING)
    private Type type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String websiteUrl) {
        this.website = websiteUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<PetPost> getPosts() {
        return posts;
    }

    public void setPost(PetPost petPost){this.posts.add(petPost);}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
