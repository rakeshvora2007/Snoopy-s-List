package com.snoopyslist.DBmodels;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rui on 4/18/17.
 */
@Entity
@Table(name = "PETPOST_SQL")
@NamedQueries({
        @NamedQuery(name = "PetPost.findAll", query = "SELECT p FROM PetPost p"),
        @NamedQuery(name = "PetPost.findById", query = "SELECT p FROM PetPost p WHERE p.id = :id"),
        @NamedQuery(name = "PetPost.findByTitle", query = "SELECT p FROM PetPost p WHERE p.title = :title"),
        @NamedQuery(name = "PetPost.deleteById", query = "DELETE FROM PetPost p WHERE p.id = :id"),
        @NamedQuery(name = "PetPost.findAnimalType", query = "SELECT p FROM PetPost p WHERE p.animalType = :animalType")
})
public class PetPost implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 100)
    private String title;

    @Column
    private String animalType;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String zipCode;

    @Embedded
    private PetDetails details;



    @ManyToOne(optional = true)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private User owner;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Column
    public String timeStamp;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public  void setTitle(String title) {
        this.title = title;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public PetDetails getDetails() {
        return details;
    }

    public void setDetails(PetDetails details) {
        this.details = details;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


}
