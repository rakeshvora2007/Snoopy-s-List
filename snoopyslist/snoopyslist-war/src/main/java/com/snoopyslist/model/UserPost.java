package com.snoopyslist.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User post object contain:
 *          PostId - Int
 *          title - String
 *          animalType - String
 *          Date (yyyy/MM/dd HH:mm:ss) - String
 */
@XmlRootElement
public class UserPost {
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime() {
        SimpleDateFormat dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.dateTime = dateTime.format(new Date());
    }

    @XmlElement(required=true)
    private int postId;
    private String title;
    private String animalType;
    private String dateTime;

    public UserPost () {}

    public UserPost (int postId, String title, String animalType, String dateTime) {
        this.postId = postId;
        this.title = title;
        this.animalType = animalType;
        this.dateTime = dateTime;
    }
}
