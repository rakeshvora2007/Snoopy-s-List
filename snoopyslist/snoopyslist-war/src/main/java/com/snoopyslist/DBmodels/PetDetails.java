package com.snoopyslist.DBmodels;


import javax.persistence.*;

/**
 * Created by rui on 4/19/17.
 */
@Embeddable
public class PetDetails
{
    @Column(name = "ANIMAL_NAME")
    private  String animalName;

    @Column(name ="GENDER", length = 7)
    private String gender;

    @Column(name = "AGE")
    private int age;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "DESCRIPTION")
    private String descrition;

    @Lob
    @Basic(fetch= FetchType.EAGER)
    @Column(name = "PICTURE")
    private byte[] picture;


    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }



}
