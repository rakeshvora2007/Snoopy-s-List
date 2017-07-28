package com.snoopyslist.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Thumnail object contain:
 *      Mandatory:
 *          postId - Int
 *          title - String
 *          animalType - String
 *          animalName - String
 *          age - Int
 *          gender - String
 *          size - String
 *      Optional:
 *         picture - String
 */
@XmlRootElement
public class Thumbnail {
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @XmlElement(required=true)
    private int postId;
    @XmlElement(required=true)
    private String title;
    @XmlElement(required=true)
    private String animalType;
    @XmlElement(required=true)
    private String animalName;
    @XmlElement(required=true)
    private int age;
    @XmlElement(required=true)
    private String gender;
    private String size;
    private String picture;

    //default constructor
    public Thumbnail () {    }

    //overloading constructor
    public Thumbnail (int postId, String title, String animalType, String animalName, int age, String gender, String size, String imageUri) {
        this.postId = postId;
        this.title = title;
        this.animalType = animalType;
        this.animalName = animalName;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.picture = imageUri;
    }
}

